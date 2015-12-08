package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Property;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_14_10_TextMethods extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_10_TextMethods.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_14_10_TextMethods().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        // draw helper lines
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.setLineWidth(0f);
        canvas.moveTo(150, 600);
        canvas.lineTo(150, 800);
        canvas.moveTo(50, 760);
        canvas.lineTo(250, 760);
        canvas.moveTo(50, 700);
        canvas.lineTo(250, 700);
        canvas.moveTo(50, 640);
        canvas.lineTo(250, 640);
        canvas.stroke();
        // draw text
        String text = "AWAY again ";
        PdfFont font = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA);
        canvas.beginText();
        canvas.setFontAndSize(font, 12);
        canvas.setTextMatrix(50, 800);
        canvas.showText(text);
        Canvas canvasModel = new Canvas(canvas, pdfDoc, pdfDoc.getDefaultPageSize());
        // TODO Bug in RootElemnt showTextAligned (divX is too small)
        canvasModel.showTextAligned(text + " Center", 150, 760, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(text + " Right", 150, 700, Property.TextAlignment.RIGHT);
        canvasModel.showTextAligned(text + " Left", 150, 640, Property.TextAlignment.LEFT);
        canvasModel.showTextAlignedKerned(text + " Left", 150, 628,
                Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        canvas.setTextMatrix(0, 1, -1, 0, 300, 600);
        canvas.showText("Position 300,600, rotated 90 degrees.");
        for (int i = 0; i < 360; i += 30) {
            canvasModel.showTextAligned(text, 400, 700, Property.TextAlignment.LEFT, i);
        }
        canvas.endText();

        pdfDoc.close();
    }
}
