/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/29037981/how-to-draw-a-rectangle-around-multiline-text
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class DrawRectangleAroundText extends GenericTest {
    public static final String DOG = "src/test/resources/img/dog.bmp";
    public static final String FOX = "src/test/resources/img/fox.bmp";
    public static final String DEST = "./target/test/resources/sandbox/objects/draw_rectangle_around_text.pdf";
    public static final String SRC = "./src/test/resources/sandbox/objects/hello.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DrawRectangleAroundText().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        Paragraph p = new Paragraph("This is a long paragraph that doesn't"
                + "fit the width we defined for the simple column of the"
                + "ColumnText object, so it will be distributed over several"
                + "lines (and we don't know in advance how many).");
        doc.showTextAligned(p.setWidth(250 - 120).setHeight(780 - 500), 120, 500, 1,
                Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        canvas.rectangle(120, 500, 130, 280);
        canvas.stroke();
        doc.showTextAligned(p.setWidth(430 - 300).setHeight(780 - 500), 300, 500, 1,
                Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        // TODO One cannot find rectangle's size before rendering
        //float endPos = ct.getYLine() - 5;
        float endPos = 500;
        canvas.rectangle(300, endPos, 130, 780 - endPos);
        canvas.stroke();
        doc.close();
    }
}
