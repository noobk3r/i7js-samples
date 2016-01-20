package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_01_PageInformation extends GenericTest {
    public static final String RESULT
            = "./target/test/resources/book/part2/chapter06/Listing_06_01_PageInformation.txt";
    public static final String CMP_RESULT
            = "./src/test/resources/book/part2/chapter06/cmp_Listing_06_01_PageInformation.txt";

    public static final String HELLO_WORLD_LANDSCAPE1 = "./src/test/resources/book/part1/chapter01/cmp_Listing_01_05_HelloWorldLandscape1.pdf";
    public static final String HELLO_WORLD_LANDSCAPE2 = "./src/test/resources/book/part1/chapter01/cmp_Listing_01_06_HelloWorldLandscape2.pdf";
    public static final String MOVIE_TEMPLATES = "./src/test/resources/book/part1/chapter03/cmp_Listing_03_29_MovieTemplates.pdf";
    public static final String HERO1 = "./src/test/resources/book/part1/chapter05/cmp_Listing_05_15_Hero1.pdf";


    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        Listing_06_01_PageInformation app = new Listing_06_01_PageInformation();
        app.arguments = args;
        app.manipulatePdf(RESULT);
    }

    @Override
    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Use old examples to create PDFs
        // Listing_01_05_HelloWorldLandscape1.main(arguments);
        // Listing_01_06_HelloWorldLandscape2.main(arguments);
        // Listing_03_29_MovieTemplates.main(arguments);
        // Listing_05_15_Hero1.main(arguments);
        // Inspecting PDFs
        PrintWriter writer = new PrintWriter(new FileOutputStream(RESULT));
        inspect(writer, HELLO_WORLD_LANDSCAPE1);
        inspect(writer, HELLO_WORLD_LANDSCAPE2);
        inspect(writer, MOVIE_TEMPLATES);
        inspect(writer, HERO1);
        writer.close();
    }

    public static void inspect(PrintWriter writer, String filename)
            throws IOException {
        PdfReader reader = new PdfReader(filename);
        PdfDocument pdfDoc= new PdfDocument(reader);
        writer.println(filename);
        writer.print("Number of pages: ");
        writer.println(pdfDoc.getNumberOfPages());
        Rectangle mediabox = pdfDoc.getDefaultPageSize();
        writer.print("Size of page 1: [");
        writer.print(mediabox.getLeft());
        writer.print(',');
        writer.print(mediabox.getBottom());
        writer.print(',');
        writer.print(mediabox.getRight());
        writer.print(',');
        writer.print(mediabox.getTop());
        writer.println("]");
        writer.print("Rotation of page 1: ");
        writer.println(pdfDoc.getFirstPage().getRotation());
        writer.print("Page size with rotation of page 1: ");
        // TODO No toString() in Rectangle
        writer.println(getPageSizeWithRotation(pdfDoc.getFirstPage()));
        writer.print("Is rebuilt? ");
        writer.println(reader.hasRebuiltXref());
        writer.print("Is encrypted? ");
        // TODO No isEncrypted()
        // writer.println(reader.isEncrypted());
        writer.println();
        writer.println();
        writer.flush();
        reader.close();
    }

    /**
     * Gets the rotated page from a page dictionary.
     * @param page the page
     * @return the rotated page rectangle
     */
    public static Rectangle getPageSizeWithRotation(final PdfPage page) {
        Rectangle rect = page.getPageSize();
        int rotation = page.getRotation();
        while (rotation > 0) {
            rect = new Rectangle(rect.getHeight(), rect.getWidth());
            rotation -= 90;
        }
        return rect;
    }

    @Override
    protected void comparePdf(String dest, String cmp) throws Exception {
        //super.comparePdf(dest, cmp);
        BufferedReader destReader = new BufferedReader(new InputStreamReader(new FileInputStream(dest)));
        BufferedReader cmpReader = new BufferedReader(new InputStreamReader(new FileInputStream(cmp)));
        String curDestStr;
        String curCmpStr;
        int row = 1;
        while ((curDestStr = destReader.readLine()) != null) {
            if ((curCmpStr = cmpReader.readLine()) != null) {
                addError("The lengths of files are different.");
            }
            if (!curCmpStr.equals(curDestStr)) {
                addError("The files are different on the row " + row );
            }
            row++;
        }
        if ((curCmpStr = cmpReader.readLine()) != null) {
            addError("The lengths of files are different.");
        }
    }

    @Override
    protected String getDest() {
        // dummy
        return RESULT;
    }

    @Override
    protected String getCmpPdf() {
        // dummy
        return CMP_RESULT;
    }
}
