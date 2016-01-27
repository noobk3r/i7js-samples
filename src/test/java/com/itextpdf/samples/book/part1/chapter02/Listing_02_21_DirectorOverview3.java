/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
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
public class Listing_02_21_DirectorOverview3 extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter02/Listing_02_21_DirectorOverview3.pdf";

    protected PdfFont bold;
    protected PdfFont normal;


    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_21_DirectorOverview3().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD); // 12
        normal = PdfFontFactory.createStandardFont(FontConstants.HELVETICA); // 12

        // Make the connection to the database
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT DISTINCT d.id, d.name, d.given_name, count(*) AS c "
                        + "FROM film_director d, film_movie_director md "
                        + "WHERE d.id = md.director_id "
                        + "GROUP BY d.id, d.name, d.given_name ORDER BY c DESC");
        Director director;
        // creates line separators
        // TODO No LineSeparator
        // Text CONNECT = new Text(new LineSeparator(0.5f, 95, BaseColor.BLUE, Element.ALIGN_CENTER, 3.5f));
        // LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
        // creates tabs
        // TODO No VerticalPositionMark
        // TODO No DottedLineSeparator
        // Text tab1 = new Text(new VerticalPositionMark(), 200, true);
        // Text tab2 = new Text(new VerticalPositionMark(), 350, true);
        // Text tab3 = new Text(new DottedLineSeparator(), 450, true);
        // loops over the directors
        while (rs.next()) {
            // creates a paragraph with the director name
            director = PojoFactory.getDirector(rs);
            Paragraph p = new Paragraph();
            for (Text text : PojoToElementFactory.getDirectorPhrase(director, bold, normal)) {
                p.add(text);
            }
            // adds a separator
            // p.add(CONNECT);
            // adds more info about the director
            p.add(String.format("movies: %d", rs.getInt("c")));
            // adds a separator
            //p.add(UNDERLINE);
            // adds the paragraph to the document
            doc.add(p);
            // gets all the movies of the current director
            Set<Movie> movies
                    = new TreeSet<>(new MovieComparator(MovieComparator.BY_YEAR));
            movies.addAll(PojoFactory.getMovies(connection, rs.getInt("id")));
            // loop over the movies
            for (Movie movie : movies) {
                // create a Paragraph with the movie title
                p = new Paragraph(movie.getMovieTitle());
                // insert a tab
                // p.add(new Text(tab1));
                // add the origina title
                if (movie.getOriginalTitle() != null)
                    p.add(new Text(movie.getOriginalTitle()));
                // insert a tab
                // p.add(new Text(tab2));
                // add the run length of the movie
                p.add(new Text(String.format("%d minutes", movie.getDuration())));
                // insert a tab
                // p.add(new Text(tab3));
                // add the production year of the movie
                p.add(new Text(String.valueOf(movie.getYear())));
                // add the paragraph to the document
                doc.add(p);
            }
            doc.add(new Paragraph("\n"));
        }
        doc.close();
        stm.close();
        connection.close();
    }
}
