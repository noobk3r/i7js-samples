package com.itextpdf.samples.book;

import com.itextpdf.basics.PdfException;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Ignore("showTextAligned not implemented")
public class Listing_01_12_HelloWorldColumn extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_01_12_HelloWorldColumn.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_01_12_HelloWorldColumn().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, PdfException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Write text to canvas directly using a convenience method
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());   //Adds new page to the document and initializes canvas by page content contentStream.
        TextRenderer.showTextAligned(canvas, 0, "Hello World", 36, 788, 0); //alignment is set to '0'. No constants for alignment are available yet.
        canvas.release();

        //Close document
        pdfDoc.close();
    }

}
