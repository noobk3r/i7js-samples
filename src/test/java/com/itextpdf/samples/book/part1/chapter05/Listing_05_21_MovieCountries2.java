package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.MovieComparator;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_05_21_MovieCountries2 extends Listing_05_20_MovieCountries1 {
    public static final String DEST = "./target/test/resources/book/part1/chapter05/Listing_05_21_MovieCountries2.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_21_MovieCountries2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        doc.setMargins(54, 36, 36, 36);

        HeaderHandler headerHandler = new HeaderHandler();
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);

        WatermarkHandler watermarkHandler = new WatermarkHandler();
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, watermarkHandler);

        template = new PdfFormXObject(new Rectangle(550, 803, 30, 30));
        PdfCanvas canvas = new PdfCanvas(template, pdfDoc);


        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);
        italic = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_OBLIQUE);
        normal = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA);

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT country, id FROM film_country ORDER BY country");
        int d = 1;
        while (rs.next()) {
            headerHandler.setHeader(rs.getString("country"));
            if (1 != d) {
                doc.add(new AreaBreak());
            }
            Set<Movie> movies =
                    new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
            movies.addAll(PojoFactory.getMovies(connection, rs.getString("id")));
            for (Movie movie : movies) {
                doc.add(new Paragraph(movie.getMovieTitle()).setFont(bold));
                if (movie.getOriginalTitle() != null)
                    doc.add(new Paragraph(movie.getOriginalTitle()).setFont(italic));
                doc.add(new Paragraph(String.format("Year: %d; run length: %d minutes",
                        movie.getYear(), movie.getDuration())).setFont(normal));
                doc.add(PojoToElementFactory.getDirectorList(movie));
            }
            d++;
        }

        canvas.beginText();
        try {
            canvas.setFontAndSize(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA), 12);
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvas.moveText(550, 803);
        canvas.showText(Integer.toString(pdfDoc.getNumOfPages()));
        canvas.endText();
        canvas.stroke();

        doc.close();
        connection.close();
    }


    public class WatermarkHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            PdfFont font = null;
            try {
                font = PdfFont.createStandardFont(docEvent.getDocument(), FontConstants.HELVETICA_BOLD);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfCanvas canvas =
                    new PdfCanvas(page.newContentStreamBefore(), page.getResources(), docEvent.getDocument());
            new Canvas(canvas, docEvent.getDocument(), docEvent.getDocument().getLastPage().getPageSize())
                    .setProperty(Property.FONT_COLOR, new DeviceGray(0.75f))
                    .setProperty(Property.FONT_SIZE, 52)
                    .setProperty(Property.FONT, font)
                    .showTextAligned(new Paragraph("FOOBAR FILM FESTIVAL"), 297.5f, 421,
                            docEvent.getDocument().getNumOfPages(), Property.TextAlignment.CENTER,
                            Property.VerticalAlignment.MIDDLE, 45);
        }
    }
}
