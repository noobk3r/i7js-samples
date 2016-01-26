package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_02_23_MovieLinks2 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter02/Listing_02_23_MovieLinks2.pdf";
    public static final String MOVIE_LINKS1 = "./src/test/resources/book/part1/chapter02/cmp_Listing_02_22_MovieLinks1.pdf";
    public static final String MOVIE_LINKS1_TODO = "Listing_02_22_MovieLinks1.pdf";


    protected PdfFont bold;
    protected PdfFont italic;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_23_MovieLinks2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String destination) throws IOException, SQLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(destination);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD);
        italic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_OBLIQUE);

        Paragraph p = new Paragraph();
        Text top = new Text("Country List").setFont(bold);
        top.setProperty(Property.DESTINATION, "top");
        // TODO setLocalDestination
        // top.setLocalDestination("top");
        p.add(top);
        doc.add(p);
        // create an external link
        Link imdb = new Link("Internet Movie Database", PdfAction.createURI("http://www.imdb.com/"));
        imdb.setFont(italic);
        p = new Paragraph("Click on a country, and you'll get a list of movies, "
                + "containing links to the ");
        p.add(imdb);
        p.add(".");
        doc.add(p);
        // Create a remote goto
        p = new Paragraph("This list can be found in a ");
        Link page1 = new Link("separate document", PdfAction.createGoToR(MOVIE_LINKS1_TODO, 1)); // TODO
        p.add(page1);
        p.add(".");
        doc.add(p);
        doc.add(new Paragraph("\n"));

        // Make the connection to the database
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT DISTINCT mc.country_id, c.country, count(*) AS c "
                        + "FROM film_country c, film_movie_country mc "
                        + "WHERE c.id = mc.country_id "
                        + "GROUP BY mc.country_id, country ORDER BY c DESC");
        // loop over the results
        while (rs.next()) {
            // add country with remote goto
            Paragraph country = new Paragraph(rs.getString("country"));
            country.add(": ");
            Link link = new Link(String.format("%d movies", rs.getInt("c")),
                    PdfAction.createGoToR(MOVIE_LINKS1_TODO, rs.getString("country_id"))); // TODO
            country.add(link);
            doc.add(country);
        }
        doc.add(new Paragraph("\n"));
        // Create local goto to top
        p = new Paragraph("Go to ");
        PdfArray array = new PdfArray();
        array.add(doc.getPdfDocument().getPage(1).getPdfObject());
        array.add(PdfName.XYZ);
        array.add(new PdfNumber(36));
        array.add(new PdfNumber(100));
        array.add(new PdfNumber(1));
        PdfDestination dest = PdfDestination.makeDestination(array);
        Link topLink = new Link("top", PdfAction.createGoTo(dest));
        p.add(topLink);
        p.add(".");
        doc.add(p);
        doc.close();
        stm.close();
        connection.close();
    }
}
