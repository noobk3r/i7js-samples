package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.exceptions.PdfException;
import com.itextpdf.core.fonts.Font;
import com.itextpdf.core.pdf.PdfContentStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_01_11_HelloWorldDirect {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException, PdfException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document and add page
        PdfDocument pdfDoc = new PdfDocument(writer);
        PdfContentStream contentStream = pdfDoc.addNewPage().getContentStream();

        //Initialize canvas and write text to it
        PdfCanvas canvas = new PdfCanvas(contentStream);
        canvas.saveState().beginText().moveText(36, 788).
                setFontAndSize(new Font(), 12).showText("Hello World").
                endText().restoreState();

        //close document
        pdfDoc.close();
    }

}
