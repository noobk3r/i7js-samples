package com.itextpdf.samples;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.basics.PdfException;
import com.itextpdf.core.fonts.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_12_StampText_Option1 {

    static private final String RESULT = "./result.pdf";
    static private final String SOURCE = "./source.pdf";

    public static void main(String args[]) throws IOException, PdfException {

        //Initialize reader
        FileInputStream fis = new FileInputStream(SOURCE);
        PdfReader reader = new PdfReader(fis);

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        //Initialize canvas and write to it
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.saveState().beginText().setFontAndSize(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, "")), 12).
                moveText(36, 540).showText("Hello people!").endText().restoreState();
        canvas.release();

        //Close document
        pdfDoc.close();

    }
}
