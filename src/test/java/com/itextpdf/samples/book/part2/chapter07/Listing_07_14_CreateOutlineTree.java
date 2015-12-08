package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfObject;
import com.itextpdf.core.pdf.PdfOutline;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_14_CreateOutlineTree extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter07/Listing_07_14_CreateOutlineTree.pdf";
    public static final String DEST_XML
            = "./target/test/resources/book/part2/chapter07/Listing_07_14_CreateOutlineTree.xml";

    /**
     * Pattern of the IMDB urls
     */
    public static final String RESOURCE = "http://imdb.com/title/tt%s/";
    /**
     * JavaScript snippet.
     */
    public static final String INFO = "app.alert('Movie produced in %s; run length: %s');";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_07_14_CreateOutlineTree().manipulatePdf(DEST);
        //application.createXml(DEST, DEST_XML);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        // TODO No Set viewer preferences
        //writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
        PdfOutline root = pdfDoc.getOutlines(false);
        if (root == null) {
            root = new PdfOutline(pdfDoc);
        }
        PdfOutline movieBookmark;
        PdfOutline link;
        PdfOutline info;
        String title;
        List<Movie> movies = PojoFactory.getMovies(connection);
        // Add page in order to have canvas to write on
        pdfDoc.addNewPage();
        for (Movie movie : movies) {
            title = movie.getMovieTitle();
            if ("3-Iron".equals(title)) {
                title = "\ube48\uc9d1";
            }
            movieBookmark = root.addOutline(title);
            movieBookmark.addAction(PdfAction.createGoTo(pdfDoc,
                    PdfExplicitDestination.createFitH(pdfDoc.getLastPage(),
                            pdfDoc.getLastPage().getPageSize().getTop())));
            // TODO No setStyle and setColor on PdfOutline
            link = movieBookmark.addOutline("link to IMDB");
            link.addAction(PdfAction.createURI(pdfDoc, (String.format(RESOURCE, movie.getImdb()))));
            info = movieBookmark.addOutline("instant info");
            info.addAction(PdfAction.createJavaScript(pdfDoc,
                    String.format(INFO, movie.getYear(), movie.getDuration())));
            doc.add(new Paragraph(movie.getMovieTitle()));
            doc.add(PojoToElementFactory.getDirectorList(movie));
            doc.add(PojoToElementFactory.getCountryList(movie));
        }
        doc.close();
        // Close the database connection
        connection.close();
    }

    /**
     * Create an XML file with named destinations
     *
     * @param src  The path to the PDF with the destinations
     * @param dest The path to the XML file
     * @throws IOException
     */
    public void createXml(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src));

        HashMap<Object, PdfObject> map = pdfDoc.getCatalog().getNamedDestinations();
        // TODO No exportToXML
        // SimpleNamedDestination.exportToXML(map, new FileOutputStream(dest),
        //        "ISO8859-1", true);
        pdfDoc.close();
    }
}
