package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_12_StampText_Option2 {

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
        Document doc = new Document(pdfDoc);

        //Add paragraph to a fixed position
        Paragraph p = new Paragraph("Hello people!").setFixedPosition(36, 540);
        doc.add(p);

        //close document
        doc.close();

    }
}
