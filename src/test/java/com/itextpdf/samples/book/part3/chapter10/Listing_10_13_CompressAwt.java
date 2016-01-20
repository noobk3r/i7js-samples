package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.awt.*;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_10_13_CompressAwt extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_13_CompressAwt.pdf";
    /** The resulting PDF file. */
    public static final String RESULT1
            = "./target/test/resources/book/part3/chapter10/hitchcock100.pdf";
    /** The resulting PDF file. */
    public static final String RESULT2
            = "./target/test/resources/book/part3/chapter10/hitchcock20.pdf";
    /** The resulting PDF file. */
    public static final String RESULT3
            = "./target/test/resources/book/part3/chapter10/hitchcock10.pdf";
    /** One of the resources. */
    public static final String RESOURCE
            = "./src/test/resources/book/part3/chapter10/hitchcock.png";

    public static void main(String args[]) throws IOException {
        new Listing_10_13_CompressAwt().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        createPdf(RESULT1, 1);
        createPdf(RESULT2, 0.2f);
        createPdf(RESULT3, 0.1f);
        // Create a file to compare via CompareTool
        concatenateResults(dest, new String[]{RESULT1, RESULT2, RESULT3});
    }

    public void createPdf(String filename, float quality) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
        Document doc = new Document(pdfDoc, new PageSize(200, 280));
        java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(RESOURCE);
        // TODO No quality argument in itext6(DEVSIX-413)
        Image img = new Image(ImageFactory.getImage(awtImage, null));
        img.setFixedPosition(15, 15);
        doc.add(img);
        doc.close();
    }

    // Only for testing reasons
    protected void concatenateResults(String dest, String[] names) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfDocument tempDoc;
        for (String name : names) {
            tempDoc = new PdfDocument(new PdfReader(name));
            tempDoc.copyPages(1, tempDoc.getNumberOfPages(), pdfDoc);
            tempDoc.close();
        }
        pdfDoc.close();
    }
}
