package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.ListItem;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_02_20_DirectorOverview2 extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter02/Listing_02_20_DirectorOverview2.pdf";

    protected PdfFont bold;
    protected PdfFont normal;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_20_DirectorOverview2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        bold = PdfFont.createStandardFont(FontConstants.HELVETICA_BOLD);
        normal = PdfFont.createStandardFont(FontConstants.HELVETICA);

        // Make the connection to the database
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT DISTINCT d.id, d.name, d.given_name, count(*) AS c "
                        + "FROM film_director d, film_movie_director md WHERE d.id = md.director_id "
                        + "GROUP BY d.id, d.name, d.given_name ORDER BY c DESC");
        Director director;
        // loop over the directors
        while (rs.next()) {
            // create a paragraph for the director
            director = PojoFactory.getDirector(rs);
            Paragraph p = new Paragraph();
            for (Text text : PojoToElementFactory.getDirectorPhrase(director, bold, normal)) {
                p.add(text);
            }
            // add a dotted line separator
            // TODO No DottedLineSeparator
            // p.add(new Text(new DottedLineSeparator()));
            // adds the number of movies of this director
            p.add(String.format("movies: %d", rs.getInt("c")));
            doc.add(p);
            // Creates a list
            List list = new List(Property.ListNumberingType.DECIMAL);
            list.setMarginLeft(36);
            list.setMarginRight(36);
            // Gets the movies of the current director
            TreeSet<Movie> movies = new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
            movies.addAll(PojoFactory.getMovies(connection, rs.getInt("id")));
            ListItem movieitem;
            // loops over the movies
            for (Movie movie : movies) {
                // creates a list item with a movie title
                movieitem = new ListItem(movie.getMovieTitle());
                // adds a vertical position mark as a separator
                // TODO No VerticalPositionMark
                // movieitem.add(new Text(new VerticalPositionMark()));
                // adds the year the movie was produced
                movieitem.add(new Paragraph().add(new Text(String.valueOf(movie.getYear()))));
                // add an arrow to the right if the movie dates from 2000 or later
                if (movie.getYear() > 1999) {
                    // TODO No PositionedArrow
                    // movieitem.add(PositionedArrow.RIGHT);
                }
                // add the list item to the list
                list.add(movieitem);
            }
            // add the list to the document
            doc.add(list);
        }
        doc.close();
        stm.close();
        connection.close();
    }
}
