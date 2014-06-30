package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.fonts.Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_12_StampText_Option1 {

    static private final String RESULT = "./result.pdf";
    static private final String SOURCE = "./source.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize reader
        FileInputStream fis = new FileInputStream(SOURCE);
        PdfReader reader = new PdfReader(fis);

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        //Initialize canvas and write to it
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage().getContentStream());
        canvas.saveState().beginText().setFontAndSize(new Font(), 12).
                moveText(36, 540).showText("Hello people!").endText().restoreState();

        //Close document
        pdfDoc.close();

    }


}
