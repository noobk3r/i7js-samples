package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_05_22_MovieSlideShow extends GenericTest {
    static public final String DEST = "./target/test/resources/book/part1/chapter05/Listing_05_22_MovieSlideShow.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter05/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_22_MovieSlideShow().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest)
            throws FileNotFoundException, SQLException, UnsupportedEncodingException, MalformedURLException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer, PdfVersion.PDF_1_5);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A5).rotate());

        //pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PresentationHandler());
        // TODO No viewer preferences
        // writer.setViewerPreferences(PdfWriter.PageModeFullScreen);
        List<Movie> movies = PojoFactory.getMovies(connection);
        Image img;
        Cell cell;
        Table table = new Table(6);
        for (Movie movie : movies) {
            img = new Image(ImageFactory.getImage(String.format(RESOURCE, movie.getImdb())));
            cell = new Cell().add(img.setAutoScaleWidth(true));
            cell.setBorder(Border.NO_BORDER);
            table.addCell(cell);
        }
        doc.add(table);
        doc.close();
    }


    public static class PresentationHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            // TODO No setTransition, setDuration, PdfTransition
//            writer.setTransition(new PdfTransition(PdfTransition.DISSOLVE, 3));
//            writer.setDuration(5);
        }
    }
}
