package com.itextpdf.samples.book.chapter10;

import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.PdfPatternCanvas;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.canvas.color.DeviceGray;
import com.itextpdf.canvas.color.DeviceRgb;
import com.itextpdf.canvas.color.PatternColor;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.core.pdf.colorspace.PdfPattern;
import com.itextpdf.core.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class Listing_10_03_TilingPatternColor extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_10_03_TilingPatternColor.pdf";
    static private final String IMG_SRC = "./src/test/resources/img/info.png";


    public static void colorRectangle(PdfCanvas canvas, Color color, float x, float y, float width, float height) {
        canvas.saveState().setFillColor(color).rectangle(x, y, width, height).fillStroke().restoreState();
    }

    public static void main(String args[]) throws IOException {
        new Listing_10_03_TilingPatternColor().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, MalformedURLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Write to canvas
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

        PdfPattern.Tiling square = new PdfPattern.Tiling(15, 15).makeIndirect(pdfDoc);
        new PdfPatternCanvas(square).setFillColor(new DeviceRgb(0xFF, 0xFF, 0x00)).setStrokeColor(new DeviceRgb(0xFF, 0x00, 0x00)).
                rectangle(5, 5, 5, 5).fillStroke().release();

        PdfPattern.Tiling ellipse = new PdfPattern.Tiling(15, 10, 20, 25).makeIndirect(pdfDoc);
        new PdfPatternCanvas(ellipse).setFillColor(new DeviceRgb(0xFF, 0xFF, 0x00)).setStrokeColor(new DeviceRgb(0xFF, 0x00, 0x00)).
                ellipse(2, 2, 13, 8).fillStroke().release();

        PdfPattern.Tiling circle = new PdfPattern.Tiling(15, 15, 10, 20, false).makeIndirect(pdfDoc);
        new PdfPatternCanvas(circle).circle(7.5f, 7.5f, 2.5f).fill().release();

        PdfPattern.Tiling line = new PdfPattern.Tiling(5, 10, false).makeIndirect(pdfDoc);
        new PdfPatternCanvas(line).setLineWidth(1).moveTo(3, -1).lineTo(3, 11).stroke().release();

        Image img = ImageFactory.getImage(IMG_SRC);
        PdfPattern.Tiling img_pattern = new PdfPattern.Tiling(20, 20, 20, 20).makeIndirect(pdfDoc);
        img_pattern.setMatrix(new PdfArray(new float[]{-0.5f, 0f, 0f, 0.5f, 0f, 0f}));
        new PdfPatternCanvas(img_pattern).addImage(img, 0, 0, 20, false);

        PdfSpecialCs.UncoloredTilingPattern uncoloredRGBCS = new PdfSpecialCs.UncoloredTilingPattern(pdfDoc, new PdfDeviceCs.Rgb());
        float[] green = {0, 1, 0};
        float[] red = {1, 0, 0};
        float[] blue = {0, 0, 1};

        colorRectangle(canvas, new PatternColor(square), 36, 696, 126, 126);
        colorRectangle(canvas, new PatternColor(ellipse), 180, 696, 126, 126);
        colorRectangle(canvas, new PatternColor(circle, uncoloredRGBCS, blue), 324, 696, 126, 126);
        colorRectangle(canvas, new PatternColor(line, new DeviceGray()), 36, 552, 126, 126);
        colorRectangle(canvas, new PatternColor(img_pattern), 36, 408, 126, 126);

        canvas.setFillColor(new PatternColor(line, uncoloredRGBCS, red)).ellipse(180, 552, 306, 678).fillStroke();
        canvas.setFillColor(new PatternColor(circle, uncoloredRGBCS, green)).ellipse(324, 552, 450, 678).fillStroke();
        canvas.setFillColor(new PatternColor(img_pattern)).ellipse(180, 408, 450, 534).fillStroke();

        canvas.release();

        //Close document
        pdfDoc.close();

    }
}
