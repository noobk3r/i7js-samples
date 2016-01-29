/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_14_20_Text2ToPdf1 extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_20_Text2ToPdf1.pdf";
    public static void main(String args[]) throws IOException {
        new Listing_14_20_Text2ToPdf1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(300, 150));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO No FontMapper
        // FontMapper arialuni = new FontMapper() {
        //     public BaseFont awtToPdf(Font font) {
        //         try {
        //             return BaseFont.createFont(
        //                     "c:/windows/fonts/arialuni.ttf",
        //                     BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        //         } catch (DocumentException e) {
        //             e.printStackTrace();
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //         return null;
        //     }
        //
        //    public Font pdfToAwt(BaseFont font, int size) {
        //         return null;
        //     }
        //
        // };
        // Create a Graphics2D object
        // TODO No PdfGraphics2D
        // Graphics2D g2 = new PdfGraphics2D(canvas, 300, 150, arialuni);
        // // Draw text to the Graphics2D
        // Listing_14_19_TextExample2 text = new Listing_14_19_TextExample2();
        // text.setSize(new Dimension(300, 150));
        // text.paint(g2);
        //g2.dispose();
        doc.close();
    }
}
