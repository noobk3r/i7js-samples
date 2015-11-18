package com.itextpdf.samples.book.part1.chapter01;

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
public class Listing_01_01_HelloWorld extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter01/Listing_01_01_HelloWorld.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_01_01_HelloWorld().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Add paragraph to the document
        doc.add(new Paragraph("Hello World!"));

        //Close document
        doc.close();
    }

}
