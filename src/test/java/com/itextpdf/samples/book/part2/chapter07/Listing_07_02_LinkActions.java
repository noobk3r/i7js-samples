/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_07_02_LinkActions extends GenericTest {
    public static final String DEST1 = "./target/test/resources/book/part2/chapter07/Listing_07_02_LinkActions.pdf";
    public static final String DEST = "./target/test/resources/book/part2/chapter07/Listing_07_02_LinkActions2.pdf";
    public static final String DEST3 = "./target/test/resources/book/part2/chapter07/Listing_07_02_LinkActions.xml";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_07_02_LinkActions().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // TODO First revise this example
        // Do not forget to make source file from MovieLinks1 cmp. Not simply uncomment the next line
        //new Listing_02_22_MovieLinks1().manipulatePdf(DEST1);
        manipulatePdf2(DEST);
        //createXml(DEST, DEST3);
    }

    public void manipulatePdf2(String dest) throws FileNotFoundException, SQLException {
        // Open the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("Click on a country, and you'll get a list of movies, containing links to the ").
                add(new Link("Internet Movie Database", PdfAction.createURI("http://www.imdb.com"))).
                add(".");
        doc.add(p);

        p = new Paragraph("This list can be found in a ").
                add(new Link("separate document", PdfAction.createGoToR("movie_links_1.pdf", 1))).
                add(".");
        doc.add(p);

        // Get a list with countries from the database
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT DISTINCT mc.country_id, c.country, count(*) AS c "
                        + "FROM film_country c, film_movie_country mc WHERE c.id = mc.country_id "
                        + "GROUP BY mc.country_id, country ORDER BY c DESC");
        // Loop over the countries
        while (rs.next()) {
            Paragraph country = new Paragraph(rs.getString("country"));
            country.add(": ");
            Link link = new Link(String.format("%d movies", rs.getInt("c")),
                    PdfAction.createGoToR("movie_links_1.pdf", rs.getString("country_id"), true));
            country.add(link);
            doc.add(country);
        }
        p = new Paragraph("Go to ").
                add(new Link("top", PdfAction.createGoTo("top"))).
                add(".");
        doc.add(p);

        PdfArray array = new PdfArray();
        array.add(pdfDoc.getFirstPage().getPdfObject());
        array.add(PdfName.XYZ);
        array.add(new PdfNumber(36));
        array.add(new PdfNumber(842));
        array.add(new PdfNumber(1));

        pdfDoc.addNewName(new PdfString("top"), array);

        //Close document
        doc.close();
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

        Map<Object, PdfObject> map = pdfDoc.getCatalog().getNamedDestinations();
        // TODO No exportToXML
        // SimpleNamedDestination.exportToXML(map, new FileOutputStream(dest),
        //        "ISO8859-1", true);
        pdfDoc.close();
    }

}
