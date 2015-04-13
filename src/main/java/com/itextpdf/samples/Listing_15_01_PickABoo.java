package com.itextpdf.samples;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_15_01_PickABoo {

    static public final String DEST = "./target/test/resources/Listing_15_01_PickABoo.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_15_01_PickABoo().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws PdfException, IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Write to canvas
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfLayer layer = new PdfLayer("Do you see me?", pdfDoc);
        layer.setOn(true);
        canvas.beginText().setFontAndSize(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, "")), 18).
                moveText(50, 760).showText("Do you see me?").
                beginLayer(layer).moveText(0, -30).showText("Peek-A-Boo!!!").endLayer().
                endText();
        canvas.release();

        //Close document
        pdfDoc.close();
    }


}
