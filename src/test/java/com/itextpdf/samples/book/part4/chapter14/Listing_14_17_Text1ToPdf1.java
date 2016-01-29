/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_14_17_Text1ToPdf1 extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_17_Text1ToPdf1.pdf";
    public static void main(String args[]) throws IOException {
        new Listing_14_17_Text1ToPdf1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(600, 60));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO No FontMapper
        // DefaultFontMapper mapper = new DefaultFontMapper();
        // mapper.insertDirectory("c:/windows/fonts/");
        // // create the Graphics2D object
        // TODO No PdfGraphics2D
        // Graphics2D g2 = new PdfGraphics2D(canvas, 600, 60, mapper);
        // // write the text to the Graphics2D (will NOT work as expected!)
        // Listing_14_16_TextExample1 text = new Listing_14_16_TextExample1();
        // text.paint(g2);
        // g2.dispose();
        doc.close();
    }
}
