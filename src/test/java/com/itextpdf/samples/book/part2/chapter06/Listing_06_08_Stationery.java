package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.MovieComparator;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_08_Stationery extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter06/Listing_06_08_Stationery.pdf";
    public static final String SOURCE
            = "./target/test/resources/book/part2/chapter06/Listing_06_08_Stationery_watermark.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part2/chapter06/loa.jpg";

    protected PdfFont bold;
    protected PdfFont italic;
    protected PdfFont normal;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_06_08_Stationery().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        // First create the original file
        createStationery(SOURCE);

        // Initialize result document
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));

        StationeryEventHandler eventHandler = new StationeryEventHandler();
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, eventHandler);

        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);
        italic = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_OBLIQUE);
        normal = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA);

        doc.setMargins(72, 36, 36, 36);
        // useStationary(writer);
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT country, id FROM film_country ORDER BY country");
        while (rs.next()) {
            doc.add(new Paragraph(rs.getString("country")).setFont(bold));
            doc.add(new Paragraph("\n"));
            Set<Movie> movies =
                    new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
            movies.addAll(PojoFactory.getMovies(connection, rs.getString("id")));
            for (Movie movie : movies) {
                doc.add(new Paragraph(movie.getMovieTitle()).setFont(bold));
                if (movie.getOriginalTitle() != null)
                    doc.add(new Paragraph(movie.getOriginalTitle()).setFont(italic));
                doc.add(new Paragraph(
                        String.format("Year: %d; run length: %d minutes",
                                movie.getYear(), movie.getDuration())).setFont(normal));
                doc.add(PojoToElementFactory.getDirectorList(movie));
            }
            if (!rs.isLast()) {
                doc.add(new AreaBreak());
            }
        }
        doc.close();

        // Close the database connection
        connection.close();
    }

    public void createStationery(String filename) throws IOException {
        // Initialize document
        FileOutputStream fos = new FileOutputStream(filename);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);

        Table table = new Table(2);
        // TODO No facility to set default cell properties
        table.addCell(new Cell()
                .add(new Paragraph("FOOBAR FILM FESTIVAL").setFont(bold))
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));
        doc.add(table);
        PdfFont font = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getLastPage().newContentStreamBefore(),
                pdfDoc.getLastPage().getResources(), pdfDoc);
        new Canvas(canvas, pdfDoc, pdfDoc.getLastPage().getPageSize())
                .setProperty(Property.FONT_COLOR, new DeviceGray(0.75f))
                .setProperty(Property.FONT_SIZE, 52)
                .setProperty(Property.FONT, font)
                .showTextAligned(new Paragraph("FOOBAR FILM FESTIVAL"), 297.5f, 421, pdfDoc.getNumOfPages(),
                        Property.TextAlignment.CENTER, Property.VerticalAlignment.MIDDLE, 45);
        doc.close();
    }


    protected class StationeryEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocument pdfDoc = ((PdfDocumentEvent) event).getDocument();
            int pageNum = pdfDoc.getPageNum(((PdfDocumentEvent) event).getPage());

            PdfReader reader = null;
            try {
                reader = new PdfReader(SOURCE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfDocument srcDoc = new PdfDocument(reader);
            PdfFormXObject watermark = null;
            try {
                watermark = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new PdfCanvas(pdfDoc.getPage(pageNum).newContentStreamBefore(),
                    pdfDoc.getPage(pageNum).getResources(), pdfDoc)
                    .addXObject(watermark, 0, 0);
        }
    }
}
