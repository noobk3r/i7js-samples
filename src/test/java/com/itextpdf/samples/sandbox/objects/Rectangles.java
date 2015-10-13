/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24582887/rectangle-overlapping-in-itext-pdf-generating
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.canvas.color.DeviceGray;
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
public class Rectangles extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/rectangles.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new OrdinalNumbers().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        float llx = 36;
        float lly = 700;
        float urx = 200;
        float ury = 806;
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        Rectangle rect1 = new Rectangle(llx, lly, urx - llx, ury - lly);
        canvas
                .setStrokeColor(Color.BLACK)
                .setLineWidth(1)
                .setFillColor(new DeviceGray(0.9f))
                .rectangle(rect1)
                .fillStroke();
        Rectangle rect2 = new Rectangle(llx + 60, lly, urx - llx - 60, ury - 40 - lly);
        canvas
                .setStrokeColor(Color.WHITE)
                .setLineWidth(0.5f)
                .setFillColor(new DeviceGray(0.1f))
                .rectangle(rect2)
                .fillStroke();

        doc.close();
    }
}
