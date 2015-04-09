package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Example demonstrates how to add paragraphs using floating and fixed layouts
 */
public class Listing_99_01_DifferentLayouts {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException, PdfException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Add floating paragraph
        doc.add(new Paragraph("Flowing paragraph"));

        //Add fixed paragraph
        Paragraph p = new Paragraph("Fixed paragraph").setFixedPosition(100, 100).setWidth(200).setHeight(200);
        doc.add(p);

        //Close document
        doc.close();

    }

}
