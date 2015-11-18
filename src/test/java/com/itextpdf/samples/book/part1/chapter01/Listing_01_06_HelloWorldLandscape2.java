package com.itextpdf.samples.book.part1.chapter01;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_01_06_HelloWorldLandscape2 extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter01/Listing_01_06_HelloWorldLandscape2.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_01_06_HelloWorldLandscape2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException {
        // Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        // Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(792, 612));

        // Add paragraph to the document
        doc.add(new Paragraph("Hello World!"));

        // Close document
        doc.close();
    }
}
