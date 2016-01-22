package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceCmyk;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

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
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

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
