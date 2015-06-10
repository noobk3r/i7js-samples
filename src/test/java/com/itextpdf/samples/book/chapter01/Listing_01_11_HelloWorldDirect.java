package com.itextpdf.samples.book.chapter01;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_01_11_HelloWorldDirect extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_01_11_HelloWorldDirect.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_01_11_HelloWorldDirect().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Initialize canvas, add page and write text to it
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.saveState().beginText().moveText(36, 600).
                setFontAndSize(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, "")), 12).showText("Hello World").
                endText().restoreState();
        canvas.release();

        //close document
        pdfDoc.close();
    }

}
