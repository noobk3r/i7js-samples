/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to a question by a customer.
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfResources;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.extgstate.PdfExtGState;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class TransparentWatermark extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hero.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/transparent_watermark.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TransparentWatermark().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        PdfCanvas under = new PdfCanvas(pdfDoc.getFirstPage().newContentStreamBefore(), new PdfResources(), pdfDoc);
        PdfFont font = PdfFontFactory.createFont(FontFactory.createFont(FontConstants.HELVETICA));
        Paragraph p = new Paragraph("This watermark is added UNDER the existing content")
                .setFont(font).setFontSize(15);
        new Canvas(under, pdfDoc, pdfDoc.getDefaultPageSize())
                .showTextAligned(p, 297, 550, 1, Property.TextAlignment.CENTER, Property.VerticalAlignment.TOP, 0);
        PdfCanvas over = new PdfCanvas(pdfDoc.getFirstPage().newContentStreamAfter(), new PdfResources(), pdfDoc);
        // Problems with the source file, need to set the right color
        over.setFillColor(Color.BLACK);
        p = new Paragraph("This watermark is added ON TOP OF the existing content")
                .setFont(font).setFontSize(15);
        new Canvas(over, pdfDoc, pdfDoc.getDefaultPageSize())
                .showTextAligned(p, 297, 500, 1, Property.TextAlignment.CENTER, Property.VerticalAlignment.TOP, 0);
        p = new Paragraph("This TRANSPARENT watermark is added ON TOP OF the existing content")
                .setFont(font).setFontSize(15);
        over.saveState();
        PdfExtGState gs1 = new PdfExtGState();
        // TODO Implement setFillOpacity(float)
        // gs1.setFillOpacity(0.5f);
        over.setExtGState(gs1);
        new Canvas(over, pdfDoc, pdfDoc.getDefaultPageSize())
                .showTextAligned(p, 297, 450, 1, Property.TextAlignment.CENTER, Property.VerticalAlignment.TOP, 0);
        over.restoreState();
        pdfDoc.close();
    }
}
