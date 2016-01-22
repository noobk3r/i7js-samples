package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_14_04_GraphicsStateOperators extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_04_GraphicsStateOperators.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_14_04_GraphicsStateOperators().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

        // line widths
        canvas.saveState();
        for (int i = 25; i > 0; i--) {
            canvas.setLineWidth((float) i / 10);
            canvas.moveTo(50, 806 - (5 * i));
            canvas.lineTo(320, 806 - (5 * i));
            canvas.stroke();
        }
        canvas.restoreState();

        // line cap
        canvas.moveTo(350, 800);
        canvas.lineTo(350, 750);
        canvas.moveTo(540, 800);
        canvas.lineTo(540, 750);
        canvas.stroke();

        canvas.saveState();
        canvas.setLineWidth(8);
        canvas.setLineCapStyle(PdfCanvasConstants.LineCapStyle.BUTT);
        canvas.moveTo(350, 790);
        canvas.lineTo(540, 790);
        canvas.stroke();
        canvas.setLineCapStyle(PdfCanvasConstants.LineCapStyle.ROUND);
        canvas.moveTo(350, 775);
        canvas.lineTo(540, 775);
        canvas.stroke();
        canvas.setLineCapStyle(PdfCanvasConstants.LineCapStyle.PROJECTING_SQUARE);
        canvas.moveTo(350, 760);
        canvas.lineTo(540, 760);
        canvas.stroke();
        canvas.restoreState();

        // join miter
        canvas.saveState();
        canvas.setLineWidth(8);
        canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.MITER);
        canvas.moveTo(387, 700);
        canvas.lineTo(402, 730);
        canvas.lineTo(417, 700);
        canvas.stroke();
        canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND);
        canvas.moveTo(427, 700);
        canvas.lineTo(442, 730);
        canvas.lineTo(457, 700);
        canvas.stroke();
        canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.BEVEL);
        canvas.moveTo(467, 700);
        canvas.lineTo(482, 730);
        canvas.lineTo(497, 700);
        canvas.stroke();
        canvas.restoreState();

        // line dash
        canvas.saveState();
        canvas.setLineWidth(3);
        canvas.moveTo(50, 660);
        canvas.lineTo(320, 660);
        canvas.stroke();
        canvas.setLineDash(6, 0);
        canvas.moveTo(50, 650);
        canvas.lineTo(320, 650);
        canvas.stroke();
        canvas.setLineDash(6, 3);
        canvas.moveTo(50, 640);
        canvas.lineTo(320, 640);
        canvas.stroke();
        canvas.setLineDash(15, 10, 5);
        canvas.moveTo(50, 630);
        canvas.lineTo(320, 630);
        canvas.stroke();
        float[] dash1 = {10, 5, 5, 5, 20};
        canvas.setLineDash(dash1, 5);
        canvas.moveTo(50, 620);
        canvas.lineTo(320, 620);
        canvas.stroke();
        float[] dash2 = {9, 6, 0, 6};
        canvas.setLineCapStyle(PdfCanvasConstants.LineCapStyle.ROUND);
        canvas.setLineDash(dash2, 0);
        canvas.moveTo(50, 610);
        canvas.lineTo(320, 610);
        canvas.stroke();
        canvas.restoreState();

        // miter limit
        PdfFormXObject hooks = new PdfFormXObject(new Rectangle(300, 120));
        PdfCanvas hooksCanvas = new PdfCanvas(hooks, pdfDoc);
        hooksCanvas.setLineWidth(8);
        hooksCanvas.moveTo(46, 50);
        hooksCanvas.lineTo(65, 80);
        hooksCanvas.lineTo(84, 50);
        hooksCanvas.stroke();
        hooksCanvas.moveTo(87, 50);
        hooksCanvas.lineTo(105, 80);
        hooksCanvas.lineTo(123, 50);
        hooksCanvas.stroke();
        hooksCanvas.moveTo(128, 50);
        hooksCanvas.lineTo(145, 80);
        hooksCanvas.lineTo(162, 50);
        hooksCanvas.stroke();
        hooksCanvas.moveTo(169, 50);
        hooksCanvas.lineTo(185, 80);
        hooksCanvas.lineTo(201, 50);
        hooksCanvas.stroke();
        hooksCanvas.moveTo(210, 50);
        hooksCanvas.lineTo(225, 80);
        hooksCanvas.lineTo(240, 50);
        hooksCanvas.stroke();

        canvas.saveState();
        canvas.setMiterLimit(2);
        canvas.addXObject(hooks, 300, 600);
        canvas.restoreState();

        canvas.saveState();
        canvas.setMiterLimit(2.1f);
        canvas.addXObject(hooks, 300, 550);
        canvas.restoreState();

        pdfDoc.close();
    }
}
