package com.itextpdf.samples.book.chapter01;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Category(SampleTest.class)
public class Listing_01_12_HelloWorldColumn extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_01_12_HelloWorldColumn.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_01_12_HelloWorldColumn().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        new Document(pdfDoc).showTextAligned("Hello World", 36, 788, Property.HorizontalAlignment.LEFT);

        //Close document
        pdfDoc.close();
    }

}
