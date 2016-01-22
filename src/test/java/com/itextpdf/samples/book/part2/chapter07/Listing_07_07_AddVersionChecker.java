package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_07_AddVersionChecker extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter07/Listing_07_07_AddVersionChecker.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part2/chapter07/viewer_version.js";

    public static final String HELLO_WORLD = "./src/test/resources/book/part1/chapter01/cmp_Listing_01_01_HelloWorld.pdf";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        Listing_07_07_AddVersionChecker checker = new Listing_07_07_AddVersionChecker();
        checker.arguments = args;
        checker.manipulatePdf(DEST);

    }

    protected static String readFileToString(String path) throws IOException {
        File file = new File(path);
        byte[] jsBytes = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        f.read(jsBytes);
        return new String(jsBytes);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Listing_01_01_HelloWorld.main(arguments);
        // Create a reader
        PdfReader reader = new PdfReader(HELLO_WORLD);
        // Create a stamper
        PdfDocument pdfDoc
                = new PdfDocument(reader, new PdfWriter(DEST));
        // Add some javascript
        pdfDoc.getCatalog()
                .setOpenAction(PdfAction.createJavaScript(pdfDoc, readFileToString(RESOURCE)));
        // Close the pdfDocument
        pdfDoc.close();
    }
}
