package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.fonts.Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_06_21_ConcatenateStamp {

    static private final String RESULT = "./result.pdf";
    static private final String SOURCE1 = "./source1.pdf";
    static private final String SOURCE2 = "./source2.pdf";

    public static void main(String args[]) throws IOException {

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
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument resultDoc = new PdfDocument(writer);

        //Copy and stamp pages from source 1 to destination
        for (int i = 1; i <= n1; i++) {
            PdfPage page = resultDoc.addPage(sourceDoc1.getPage(i));
            PdfCanvas canvas = new PdfCanvas(page.getContentStream());
            canvas.saveState().beginText().setFontAndSize(new Font(), 12).
                    moveText(36, 540).showText("Hello World!").endText().restoreState();
            //Flush the page immediately to reduce memory consumption
            page.flush();
        }

        //Copy and stamp pages from source 2 to destination
        for (int i = 1; i <= n2; i++) {
            PdfPage page = resultDoc.addPage(sourceDoc2.getPage(i));
            PdfCanvas canvas = new PdfCanvas(page.getContentStream());
            canvas.saveState().beginText().setFontAndSize(new Font(), 12).
                    moveText(36, 540).showText("Hello World!").endText().restoreState();
            //Flush the page immediately to reduce memory consumption
            page.flush();
        }

        //Close documents
        resultDoc.close();
        sourceDoc1.close();
        sourceDoc2.close();

    }


}
