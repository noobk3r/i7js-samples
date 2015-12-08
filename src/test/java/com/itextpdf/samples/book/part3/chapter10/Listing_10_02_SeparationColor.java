package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_10_02_SeparationColor extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_02_SeparationColor.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_10_02_SeparationColor().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, MalformedURLException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO NO PdfSpotColor
//        PdfSpotColor psc_g = new PdfSpotColor(
//                "iTextSpotColorGray", new GrayColor(0.9f));
//        PdfSpotColor psc_rgb = new PdfSpotColor(
//                "iTextSpotColorRGB", new BaseColor(0x64, 0x95, 0xed));
//        PdfSpotColor psc_cmyk = new PdfSpotColor(
//                "iTextSpotColorCMYK", new CMYKColor(0.3f, .9f, .3f, .1f));
//
//        colorRectangle(canvas, new SpotColor(psc_g, 0.5f), 36, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.1f), 90, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.2f), 144, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.3f), 198, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.4f), 252, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.5f), 306, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.6f), 360, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_rgb, 0.7f), 416, 770, 36, 36);
//        colorRectangle(canvas, new SpotColor(psc_cmyk, 0.25f), 470, 770, 36, 36);
//
//        canvas.setColorFill(psc_g, 0.5f);
//        canvas.rectangle(36, 716, 36, 36);
//        canvas.fillStroke();
//        canvas.setColorFill(psc_g, 0.9f);
//        canvas.rectangle(90, 716, 36, 36);
//        canvas.fillStroke();
//        canvas.setColorFill(psc_rgb, 0.5f);
//        canvas.rectangle(144, 716, 36, 36);
//        canvas.fillStroke();
//        canvas.setColorFill(psc_rgb, 0.9f);
//        canvas.rectangle(198, 716, 36, 36);
//        canvas.fillStroke();
//        canvas.setColorFill(psc_cmyk, 0.5f);
//        canvas.rectangle(252, 716, 36, 36);
//        canvas.fillStroke();
//        canvas.setColorFill(psc_cmyk, 0.9f);
//        canvas.rectangle(306, 716, 36, 36);
//        canvas.fillStroke();
//
//        // step 5
//        document.close();
    }
}
