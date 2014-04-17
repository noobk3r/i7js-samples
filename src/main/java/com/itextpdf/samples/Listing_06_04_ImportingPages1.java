package com.itextpdf.samples;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_04_ImportingPages1 {

    static private final String RESULT = "./result.pdf";
    static private final String SOURCE = "./source.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize source document
        FileInputStream fis = new FileInputStream(SOURCE);
        PdfReader reader = new PdfReader(fis);
        reader.setCloseStream(true);
        PdfDocument sourceDoc = new PdfDocument(reader);

        //Initialize destination document
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);
        writer.setCloseStream(true);
        PdfDocument resultDoc = new PdfDocument(writer);

        //Copy pages from source to destination
        for (int i = 1; i <= sourceDoc.getNumOfPages(); i++) {
            resultDoc.addPage(sourceDoc.getPage(i));
        }

        //Close documents
        resultDoc.close();
        sourceDoc.close();

    }


}
