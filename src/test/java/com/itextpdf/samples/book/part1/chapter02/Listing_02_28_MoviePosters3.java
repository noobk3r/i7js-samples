package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_02_28_MoviePosters3 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter02/Listing_02_28_MoviePosters3.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    protected PdfFont bold;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_28_MoviePosters3().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        // Initialize document
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // TODO No setStrictImageSequence(boolean) on FileOutputStream
        // TODO No setInitialLeading
        // writer.setStrictImageSequence(true);
        // writer.setInitialLeading(18);

        bold = PdfFont.createStandardFont(FontConstants.HELVETICA_BOLD);

        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            // Create an image
            Image img = new Image(ImageFactory.getImage(String.format(RESOURCE, movie.getImdb())));
            img.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
            // TODO No Image.TEXTWRAP
            img.setBorder(new SolidBorder(Color.WHITE, 10));
            img.scaleToFit(1000, 72);
            doc.add(img);
            // Create text elements
            doc.add(new Paragraph(movie.getMovieTitle()).setFont(bold).setFixedLeading(18));
            doc.add(PojoToElementFactory.getCountryList(movie));
            doc.add(new Paragraph(String.format("Year: %d", movie.getYear())).setFixedLeading(18));
            doc.add(new Paragraph(
                    String.format("Duration: %d minutes", movie.getDuration())).setFixedLeading(18));
            doc.add(new Paragraph("Directors:").setFixedLeading(18));
            doc.add(PojoToElementFactory.getDirectorList(movie));
            doc.add(new Paragraph("\n").setFixedLeading(18));
        }

        doc.close();
    }
}
