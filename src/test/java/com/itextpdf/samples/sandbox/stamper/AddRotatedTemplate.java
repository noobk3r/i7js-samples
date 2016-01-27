/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/25808367/rotate-multiline-text-with-columntext-itextsharp
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddRotatedTemplate extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_rotated_template.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddRotatedTemplate().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        PdfFormXObject formXObject = new PdfFormXObject(new Rectangle(80, 120));
        new Canvas(formXObject, pdfDoc).add(new Paragraph("Some long text that needs to be distributed over several lines."));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.addXObject(formXObject, 36, 600);
        double angle = Math.PI / 4;
        canvas.addXObject(formXObject,
                (float) Math.cos(angle), -(float) Math.sin(angle),
                (float) Math.cos(angle), (float) Math.sin(angle),
                150, 600);

        pdfDoc.close();
    }
}
