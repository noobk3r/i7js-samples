package com.itextpdf.samples;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfDocumentInfo;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_12_01_MetadataPdf {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);
        writer.setCloseStream(true);

        //Initialize document and add page
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addPage(new PdfPage(pdfDoc));

        PdfDocumentInfo info = pdfDoc.getInfo();
        info.setTitle("Hello World example").setAuthor("Bruno Lowagie").
                setSubject("This example shows how to add metadata").
                setKeywords("Metadata, iText, PDF").
                setCreator("My program using iText");

        //Close document
        pdfDoc.close();

    }

}
