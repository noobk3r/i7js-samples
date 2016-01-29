/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("Page rotation was applied to content stream in iText5 during wrapping the content. Need to investigate and implement if needed. DEVSIX-431")
@Category(SampleTest.class)
public class AddSpotColorShape extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_spot_color_shape.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddSpotColorShape().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());

        canvas.arc(0, 0, 842, 595, 0, 360);
        canvas.arc(25, 25, 817, 570, 0, 360);
        canvas.arc(50, 50, 792, 545, 0, 360);
        canvas.arc(75, 75, 767, 520, 0, 360);
        canvas.eoClip();
        canvas.newPath();
        // TODO No PdfSpotColor
        // PdfSpotColor psc = new PdfSpotColor("mySpotColor", new CMYKColor(0.8f, 0.3f, 0.3f, 0.1f));
        // canvas.setFillColor((new SpotColor(psc, 0.4f));
        canvas.setFillColor(new DeviceCmyk(0.8f, 0.3f, 0.3f, 0.1f));
        canvas.rectangle(0, 0, 842, 595);
        canvas.fill();

        pdfDoc.close();
    }
}
