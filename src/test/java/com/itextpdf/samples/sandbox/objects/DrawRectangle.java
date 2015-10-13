/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/21718712/itext-pdfcontentbyte-rectanglerectangle-does-not-behave-as-expected
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class DrawRectangle extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/draw_rectangle.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DrawRectangle().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        Rectangle rect = new Rectangle(36, 36, 559 - 36, 806 - 36);
        canvas.setLineWidth(2);
        canvas.rectangle(rect);
        canvas.stroke();
        doc.close();
    }
}
