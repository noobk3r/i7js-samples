package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.PdfLayer;
import com.itextpdf.core.fonts.Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_15_01_PickABoo {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);
        writer.setCloseStream(true);

        //Initialize document and page
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addPage(new PdfPage(pdfDoc));

        //Write to canvas
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getCurrentPage().getContentStream());
        PdfLayer layer = new PdfLayer("Do you see me?", pdfDoc);
        layer.setOn(true);
        canvas.beginText().setFontAndSize(new Font(), 18).
                moveText(50, 790).showText("Do you see me?").
                beginLayer(layer).moveText(50, 766).showText("Peek-A-Boo!!!").endLayer().
                endText();

        //Close document
        pdfDoc.close();

    }


}
