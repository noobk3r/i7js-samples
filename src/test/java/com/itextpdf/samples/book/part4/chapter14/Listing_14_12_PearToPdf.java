package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_14_12_PearToPdf extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_12_PearToPdf.pdf";
    public static void main(String args[]) throws IOException {
        new Listing_14_12_PearToPdf().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        pdfDoc.setDefaultPageSize(new PageSize(150, 150));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO No PdfGraphics2D
        // Graphics2D g2 = new PdfGraphics2D(canvas, 150, 150);
        // Listing_14_11_PearExample pear = new Listing_14_11_PearExample();
        // pear.paint(g2);
        // g2.dispose();
        pdfDoc.close();
    }
}
