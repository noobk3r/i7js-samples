package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_15_01_PeekABoo extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter15/Listing_15_01_PickABoo.pdf";
    public static String RESULT2 = "./target/test/resources/book/part4/chapter15/Listing_15_01_PickABoo2.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_15_01_PeekABoo().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        createPdf(DEST, true);
        createPdf(RESULT2, false);
    }

    public void createPdf(String dest, boolean on) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer, PdfVersion.PDF_1_5);

        // TODO No setViewerPreferences
        // PageModeUseOC

        //Write to canvas
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfLayer layer = new PdfLayer("Do you see me?", pdfDoc);
        layer.setOn(on);
        canvas.beginText().setFontAndSize(PdfFont.createStandardFont(FontConstants.HELVETICA), 18).
                moveText(50, 760).showText("Do you see me?").
                beginLayer(layer).moveText(0, -30).showText("Peek-A-Boo!!!").endLayer().
                endText();
        canvas.release();

        //Close document
        pdfDoc.close();
    }
}
