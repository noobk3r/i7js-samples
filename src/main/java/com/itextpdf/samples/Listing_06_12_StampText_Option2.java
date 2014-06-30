package com.itextpdf.samples;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.elements.ElementPosition;
import com.itextpdf.model.layout.ILayoutMgr;
import com.itextpdf.model.layout.shapes.StartingPointShape;
import com.itextpdf.model.elements.Paragraph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_12_StampText_Option2 {

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
        Document doc = new Document(pdfDoc);

        //Add paragraph to a fixed position
        Paragraph p = new Paragraph("Hello people!");
        p.setRequestedPosition(new ElementPosition(new StartingPointShape(36, 540), ILayoutMgr.Fixed));
        doc.add(p);

        //close document
        doc.close();

    }
}
