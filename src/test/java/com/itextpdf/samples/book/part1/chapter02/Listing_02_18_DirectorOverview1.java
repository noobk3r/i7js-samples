package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
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
import java.util.Set;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_02_18_DirectorOverview1 extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter02/Listing_02_18_DirectorOverview1.pdf";

    protected PdfFont bold;
    protected PdfFont normal;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_18_DirectorOverview1().manipulatePdf(DEST);
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
                        + "FROM film_director d, film_movie_director md "
                        + "WHERE d.id = md.director_id "
                        + "GROUP BY d.id, d.name, d.given_name ORDER BY name");
        Director director;
        // creating separators
        // TODO No LineSeparator
        // LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
        //Paragraph stars = new Paragraph().setFixedLeading(20);
        StarSeparator stars = new StarSeparator();
        stars.setMarginBottom(30);
        // looping over the directors
        while (rs.next()) {
            // get the director object and use it in a Paragraph
            director = PojoFactory.getDirector(rs);
            Paragraph p = new Paragraph();
            for (Text text : PojoToElementFactory.getDirectorPhrase(director, bold, normal)) {
                p.add(text);
            }
            // if there are more than 2 movies for this director
            // an arrow is added to the left
            if (rs.getInt("c") > 2)
                // TODO No PositionedArrow
                // p.add(PositionedArrow.LEFT);
                // p.add(line);
                // add the paragraph with the arrow to the document
                doc.add(p);

            // Get the movies of the directory, ordered by year
            Set<Movie> movies = new TreeSet<>(
                    new MovieComparator(MovieComparator.BY_YEAR));
            movies.addAll(PojoFactory.getMovies(connection, rs.getInt("id")));
            // loop over the movies
            for (Movie movie : movies) {
                p = new Paragraph(movie.getMovieTitle());
                p.add(": ");
                p.add(new Text(String.valueOf(movie.getYear())));
                if (movie.getYear() > 1999)
                    // TODO No PositionedArrow
                    // p.add(PositionedArrow.RIGHT);
                    doc.add(p);
            }
            // add a star separator after the director info is added
            doc.add(stars);
        }
        doc.close();
        stm.close();
        connection.close();
    }
}
