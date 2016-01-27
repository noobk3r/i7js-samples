/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27906725/itext-placement-of-phrase-within-columntext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ColumnTextAscender extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/column_text_ascender.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColumnTextAscender().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        pdfDoc.addNewPage();
        Rectangle rect = new Rectangle(50, 750, 200, 50);
        addColumn(pdfDoc, rect, false);
        rect = new Rectangle(300, 750, 200, 50);
        addColumn(pdfDoc, rect, true);
        pdfDoc.close();
    }

    public void addColumn(PdfDocument pdfDoc, Rectangle rect, boolean useAscender) {
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.setLineWidth(0.5f).setStrokeColor(Color.RED).rectangle(rect).stroke();
        Paragraph p = new Paragraph("This text is added at the top of the column.");
        // TODO No setUseAscender(boolean)
        new Canvas(canvas, pdfDoc, rect).add(p.setFixedPosition(rect.getX(), rect.getBottom(), rect.getWidth()));
//                .showTextAligned(p, rect.getLeft(), rect.getTop(), 1,
//                        Property.TextAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
    }
}
