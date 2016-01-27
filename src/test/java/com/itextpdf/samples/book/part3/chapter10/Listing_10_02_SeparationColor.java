package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceCmyk;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.colorspace.PdfColorSpace;
import com.itextpdf.core.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.core.pdf.function.PdfFunction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_10_02_SeparationColor extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_02_SeparationColor.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_10_02_SeparationColor().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, MalformedURLException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfColorSpace rgbColorSpace = createRgbColorSpace(pdfDoc, 0.39216f, 0.58431f, 0.92941f);
        PdfColorSpace grayColorSpace = createGrayColorSpace(pdfDoc, .9f);
        PdfColorSpace cmykColorSpace = createCmykColorSpace(pdfDoc, .3f, .9f, .3f, .1f);

        colorRectangle(canvas, new Color(grayColorSpace, new float[]{0.5f}), 36, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.1f}), 90, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.2f}), 144, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.3f}), 198, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.4f}), 252, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.5f}), 306, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.6f}), 360, 770, 36, 36);
        colorRectangle(canvas, new Color(rgbColorSpace, new float[]{.7f}), 416, 770, 36, 36);
        colorRectangle(canvas, new Color(cmykColorSpace, new float[]{.25f}), 470, 770, 36, 36);

        canvas.setFillColor(new Color(grayColorSpace, new float[]{0.5f}));
        canvas.rectangle(36, 716, 36, 36);
        canvas.fillStroke();
        canvas.setFillColor(new Color(grayColorSpace, new float[]{0.9f}));
        canvas.rectangle(90, 716, 36, 36);
        canvas.fillStroke();
        canvas.setFillColor(new Color(rgbColorSpace, new float[]{0.5f}));
        canvas.rectangle(144, 716, 36, 36);
        canvas.fillStroke();
        canvas.setFillColor(new Color(rgbColorSpace, new float[]{0.9f}));
        canvas.rectangle(198, 716, 36, 36);
        canvas.fillStroke();
        canvas.setFillColor(new Color(cmykColorSpace, new float[]{0.5f}));
        canvas.rectangle(252, 716, 36, 36);
        canvas.fillStroke();
        canvas.setFillColor(new Color(cmykColorSpace, new float[]{0.9f}));
        canvas.rectangle(306, 716, 36, 36);
        canvas.fillStroke();
//
//        // step 5
        pdfDoc.close();
    }

    private PdfColorSpace createGrayColorSpace(PdfDocument pdfDoc, float gray) {
        float[] c1 = new float[]{gray};
        float[] c0 = new float[]{1};
        PdfFunction f = new PdfFunction.Type2(pdfDoc, new PdfArray(new float[]{0, 1}), null, new PdfArray(c0), new PdfArray(c1), new PdfNumber(1));
        PdfSpecialCs.Separation cs = new PdfSpecialCs.Separation(pdfDoc, "iTextSpotColorGray", new DeviceGray().getColorSpace(), f);
        return cs;
    }

    private PdfColorSpace createRgbColorSpace(PdfDocument pdfDoc, float red, float green, float blue) {
        float[] c0 = new float[]{1, 1, 1};
        float[] c1 = new float[]{red, green, blue};
        PdfFunction f = new PdfFunction.Type2(pdfDoc, new PdfArray(new float[]{0, 1}), null, new PdfArray(c0), new PdfArray(c1), new PdfNumber(1));
        PdfSpecialCs.Separation cs = new PdfSpecialCs.Separation(pdfDoc, "iTextSpotColorRGB", new DeviceRgb().getColorSpace(), f);

        return cs;
    }

    private PdfColorSpace createCmykColorSpace(PdfDocument pdfDoc, float c, float m, float y, float k) {
        float[] c0 = new float[]{0, 0, 0, 0};
        float[] c1 = new float[]{c, m, y, k};
        PdfFunction f = new PdfFunction.Type2(pdfDoc, new PdfArray(new float[]{0, 1}), null, new PdfArray(c0), new PdfArray(c1), new PdfNumber(1));
        PdfSpecialCs.Separation cs = new PdfSpecialCs.Separation(pdfDoc, "iTextSpotColorCMYK", new DeviceCmyk(c, m, y, k).getColorSpace(), f);

        return cs;
    }

    private void colorRectangle(PdfCanvas canvas,
                               Color color, float x, float y, float width, float height) {
        canvas.saveState();
        canvas.setFillColor(color);
        canvas.rectangle(x, y, width, height);
        canvas.fillStroke();
        canvas.restoreState();
    }
}
