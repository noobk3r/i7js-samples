package com.itextpdf.samples.book;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_04_ImportingPages1 extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_06_04_ImportingPages1.pdf";
    static public final String SOURCE = "./src/test/resources/source.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_06_04_ImportingPages1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws PdfException, IOException {
        //Initialize source document
        FileInputStream fis = new FileInputStream(SOURCE);
        PdfReader reader = new PdfReader(fis);
        PdfDocument sourceDoc = new PdfDocument(reader);

        //Initialize destination document
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument resultDoc = new PdfDocument(writer);

        //Copy pages from source to destination
        sourceDoc.copyPages(1, sourceDoc.getNumOfPages(), resultDoc);

        //Close documents
        resultDoc.close();
        sourceDoc.close();

    }

}
