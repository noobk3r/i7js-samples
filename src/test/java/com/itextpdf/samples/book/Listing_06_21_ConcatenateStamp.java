package com.itextpdf.samples.book;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.basics.PdfException;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_21_ConcatenateStamp extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_06_21_ConcatenateStamp.pdf";
    static public final String SOURCE1 = "./src/test/resources/source.pdf";
    static private final String SOURCE2 = "./src/test/resources/source2.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_06_21_ConcatenateStamp().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws PdfException, IOException {
        //Initialize source document 1
        FileInputStream fis1 = new FileInputStream(SOURCE1);
        PdfReader reader1 = new PdfReader(fis1);
        PdfDocument sourceDoc1 = new PdfDocument(reader1);
        int n1 = sourceDoc1.getNumOfPages();

        //Initialize source document 1
        FileInputStream fis2 = new FileInputStream(SOURCE2);
        PdfReader reader2 = new PdfReader(fis2);
        PdfDocument sourceDoc2 = new PdfDocument(reader2);
        int n2 = sourceDoc2.getNumOfPages();

        //Initialize destination document
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument resultDoc = new PdfDocument(writer);

        //Copy and stamp pages from source 1 to destination
        for (int i = 1; i <= n1; i++) {
            PdfPage page = sourceDoc1.getPage(i).copy(resultDoc);
            page = resultDoc.addPage(page);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.saveState().beginText().setFontAndSize(new PdfType1Font(resultDoc, new Type1Font(FontConstants.HELVETICA, "")), 12).
                    moveText(36, 540).showText("Hello World!").endText().restoreState();
            canvas.release();
            //Flush the page immediately to reduce memory consumption
            page.flush();
        }

        //Copy and stamp pages from source 2 to destination
        for (int i = 1; i <= n2; i++) {
            PdfPage page = sourceDoc2.getPage(i).copy(resultDoc);
            resultDoc.addPage(page);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.saveState().beginText().setFontAndSize(new PdfType1Font(resultDoc, new Type1Font(FontConstants.HELVETICA, "")), 12).
                    moveText(36, 540).showText("Hello World!").endText().restoreState();
            //Flush the page immediately to reduce memory consumption
            canvas.release();
            page.flush();
        }

        //Close documents
        resultDoc.close();
        sourceDoc1.close();
        sourceDoc2.close();
    }


}
