package com.itextpdf.samples.book.chapter02;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import org.junit.experimental.categories.Category;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@Category(SampleTest.class)
public class Listing_02_10_MovieChain extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_02_10_MovieChain.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_10_MovieChain().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, SQLException, UnsupportedEncodingException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        List<Movie> kubrick = PojoFactory.getMovies(connection, 1);
        connection.close();

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(240, 240, 10, 10, 10, 10));
        // create a long Stringbuffer with movie titles
        StringBuffer buf1 = new StringBuffer();
        for (Movie movie : kubrick) {
            // replace spaces with non-breaking spaces
            buf1.append(movie.getMovieTitle().replace(' ', '\u00a0'));
            // use pipe as separator
            buf1.append('|');
        }
        Text chunk1 = new Text(buf1.toString());
        // wrap the chunk in a paragraph and add it to the document
        Paragraph paragraph = new Paragraph("A:\u00a0");
        paragraph.add(chunk1);
        paragraph.setHorizontalAlignment(Property.HorizontalAlignment.JUSTIFIED);
        doc.add(paragraph);

        // define the pipe character as split character
        chunk1.setSplitCharacters(new PipeSplitCharacter());
        // wrap the chunk in a second paragraph and add it
        paragraph = new Paragraph("B:\u00a0");
        paragraph.add(chunk1);
        paragraph.setHorizontalAlignment(Property.HorizontalAlignment.JUSTIFIED);
        doc.add(paragraph);

        // create a new StringBuffer with movie titles
        StringBuffer buf2 = new StringBuffer();
        for (Movie movie : kubrick) {
            buf2.append(movie.getMovieTitle());
            buf2.append('|');
        }
        // Create a second chunk
        Text chunk2 = new Text(buf2.toString());
        // wrap the chunk in a paragraph and add it to the document
        paragraph = new Paragraph("C:\u00a0");
        paragraph.add(chunk2);
        paragraph.setHorizontalAlignment(Property.HorizontalAlignment.JUSTIFIED);
        doc.add(paragraph);

        doc.add(new AreaBreak());

        // TODO port the rest when hyphenation is implemented.

        //Close document
        doc.close();
    }

}
