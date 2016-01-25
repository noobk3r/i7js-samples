package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_14_07_TransformationMatrix2 extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_07_TransformationMatrix2.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part4/chapter14/logo.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_14_06_TransformationMatrix1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        pdfDoc.setDefaultPageSize(new PageSize(new Rectangle(-595, -842, 595*2, 842*2)));

        // draw the coordinate system
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.moveTo(-595, 0);
        canvas.lineTo(595, 0);
        canvas.moveTo(0, -842);
        canvas.lineTo(0, 842);
        canvas.stroke();

        // read the PDF with the logo
        PdfDocument srcDoc = new PdfDocument(new PdfReader(RESOURCE));
        PdfPage curPage = srcDoc.getPage(1);
        PdfFormXObject xObject = curPage.copyAsFormXObject(pdfDoc);
        // add it at different positions using different transformations
        canvas.addXObject(xObject, 0, 0);
        canvas.addXObject(xObject, 0.5f, 0, 0, 0.5f, -595, 0);
        canvas.addXObject(xObject, 0.5f, 0, 0, 0.5f, -297.5f, 297.5f);
        canvas.addXObject(xObject, 1, 0, 0.4f, 1, -750, -650);
        canvas.addXObject(xObject, 0, -1, -1, 0, 650, 0);
        canvas.addXObject(xObject, 0, -0.2f, -0.5f, 0, 350, 0);

        pdfDoc.close();
        srcDoc.close();
    }
}
