/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_04_22_Zhang extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_22_Zhang.pdf";
    public static final String RESOURCE = "./src/test/resources/img/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_22_Zhang().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // Create a table and fill it with movies
        List<Movie> movies = PojoFactory.getMovies(connection, 3);
        Table table = new Table(new float[]{1, 5, 5, 1});
        table.setWidth(600);
        for (Movie movie : movies) {
            table.addCell(String.valueOf(movie.getYear()));
            table.addCell(movie.getMovieTitle());
            table.addCell(movie.getOriginalTitle());
            table.addCell(String.valueOf(movie.getDuration()));
        }
        // set the total width of the table
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        // draw the first two columns on one page
        // TODO No writeSelectedRows
        // table.writeSelectedRows(0, 2, 0, -1, 236, 806, canvas);
        doc.add(new AreaBreak());
        // draw the remaining two columns on the next page
        // table.writeSelectedRows(2, -1, 0, -1, 36, 806, canvas);

        doc.close();
        connection.close();
    }
}

