package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_02_25_MoviePosters1 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter02/Listing_02_25_MoviePosters1.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02//posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_25_MoviePosters1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Rectangle rect = new Rectangle(0, 806, 36, 842);
        // TODO Cannot add rectangle on document (the book describes this certain add-to-document facility)
        // We set stroke color in order to draw the border with red color
        new PdfCanvas(pdfDoc.addNewPage())
                .saveState()
                .setStrokeColor(Color.RED)
                .setFillColor(Color.RED)
                .rectangle(rect)
                .fillStroke()
                .restoreState();
        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            doc.add(new Paragraph(movie.getMovieTitle()));
            // Add an image
            doc.add(new Image(ImageFactory.getImage(String.format(RESOURCE, movie.getImdb()))));
        }

        doc.close();
    }
}
