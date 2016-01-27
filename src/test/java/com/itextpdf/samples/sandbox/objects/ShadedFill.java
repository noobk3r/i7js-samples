/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/26586093/how-to-add-a-shading-pattern-to-a-custom-shape-in-itext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.core.pdf.colorspace.PdfPattern;
import com.itextpdf.core.pdf.colorspace.PdfShading;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ShadedFill extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/shaded_fill.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ShadedFill().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        float x = 36;
        float y = 740;
        float side = 70;
        PdfShading.Axial axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), x, y, Color.PINK.getColorValue(),
                x + side, y, Color.BLUE.getColorValue(), pdfDoc);
        PdfPattern.Shading shading = new PdfPattern.Shading(axial);
        canvas.setFillColorShading(shading);
        canvas.moveTo(x, y);
        canvas.lineTo(x + side, y);
        canvas.lineTo(x + (side / 2), (float) (y + (side * Math.sin(Math.PI / 3))));
        canvas.closePathFillStroke();

        doc.close();
    }
}
