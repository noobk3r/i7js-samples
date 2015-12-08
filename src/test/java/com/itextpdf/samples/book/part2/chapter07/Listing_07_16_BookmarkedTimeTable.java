package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfOutline;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.samples.book.part1.chapter03.Listing_03_29_MovieTemplates;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.PojoFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_16_BookmarkedTimeTable extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter07/Listing_07_16_BookmarkedTimeTable.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part2/chapter07/print_page.js";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        Listing_07_16_BookmarkedTimeTable application = new Listing_07_16_BookmarkedTimeTable();
        application.arguments = args;
        application.manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        Listing_03_29_MovieTemplates.main(arguments);
        // Create a reader
        PdfReader reader = new PdfReader(Listing_03_29_MovieTemplates.DEST);
        // Create a stamper
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(dest));
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        // Create a list with bookmarks
        PdfOutline root = pdfDoc.getOutlines(false);
        if (root == null) {
            root = new PdfOutline(pdfDoc);
        }
        root.setTitle("Calendar");
        int page = 1;
        PdfOutline kid;
        List<Date> days = PojoFactory.getDays(connection);
        for (Date day : days) {
            kid = root.addOutline(day.toString());
            // See pdf-reference Table 8.50; first page should be defined as 0 here
            kid.addAction(PdfAction.createGoTo(pdfDoc, PdfExplicitDestination.createFit(page-1)));
            page++;
        }
        // Close the stamper
        pdfDoc.close();
        reader.close();
        // Close the database connection
        connection.close();
    }
}
