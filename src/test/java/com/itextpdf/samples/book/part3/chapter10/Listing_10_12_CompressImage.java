package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_10_12_CompressImage extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_12_CompressImage.pdf";
    /** The resulting PDF file. */
    public static final String RESULT1
            = "./target/test/resources/book/part3/chapter10/uncompressed.pdf";
    /** The resulting PDF file. */
    public static final String RESULT2
            = "./target/test/resources/book/part3/chapter10/compressed.pdf";
    /** One of the resources. */
    public static final String RESOURCE
            = "./src/test/resources/book/part3/chapter10/butterfly.bmp";

    public static void main(String args[]) throws IOException {
        new Listing_10_12_CompressImage().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        createPdf(RESULT1, false);
        createPdf(RESULT2, true);
        concatenateResults(dest, new String[]{RESULT1, RESULT2});
    }

    public void createPdf(String filename, boolean compress) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
        Document doc = new Document(pdfDoc);
        Image img = new Image(ImageFactory.getImage(RESOURCE));
        if (compress) {
            img.getXObject().getPdfObject().setCompressionLevel(9);
        }
        doc.add(img);
        doc.close();
    }

    // Only for testing reasons
    protected void concatenateResults(String dest, String[] names) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfDocument tempDoc;
        for (String name : names) {
            tempDoc = new PdfDocument(new PdfReader(name));
            tempDoc.copyPages(1, tempDoc.getNumOfPages(), pdfDoc);
            tempDoc.close();
        }
        pdfDoc.close();
    }
}
