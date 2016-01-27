/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_12_StampText_Option2 extends GenericTest {

    static public final String DEST = "./target/test/resources/book/part2/chapter06/Listing_06_12_StampText_Option2.pdf";
    static public final String SOURCE = "./src/test/resources/source.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_06_12_StampText_Option2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize reader
        FileInputStream fis = new FileInputStream(SOURCE);
        PdfReader reader = new PdfReader(fis);

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document doc = new Document(pdfDoc);

        //Add paragraph to a fixed position
        Paragraph p = new Paragraph("Hello people!").setFixedPosition(7, 36, 540, 300);
        doc.add(p);

        //close document
        doc.close();
    }
}
