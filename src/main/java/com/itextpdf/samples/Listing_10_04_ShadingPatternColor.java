package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.canvas.color.PatternColor;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.core.pdf.colorspace.PdfPattern;
import com.itextpdf.core.pdf.colorspace.PdfShading;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_10_04_ShadingPatternColor {

    static public final String DEST = "./target/test/resources/Listing_10_04_ShadingPatternColor.pdf";

    public static void colorRectangle(PdfCanvas canvas, Color color, float x, float y, float width, float height) throws PdfException {
        canvas.saveState().setFillColor(color).rectangle(x, y, width, height).fillStroke().restoreState();
    }

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_10_04_ShadingPatternColor().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, PdfException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Write to canvas
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

        com.itextpdf.core.pdf.colorspace.PdfShading axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), 36, 716, new float[]{1, .784f, 0}, 396, 788, new float[]{0, 0, 1}, new boolean[] {true, true}, pdfDoc);
        canvas.paintShading(axial);
        canvas = new PdfCanvas(pdfDoc.addNewPage());
        com.itextpdf.core.pdf.colorspace.PdfShading radial = new PdfShading.Radial(new PdfDeviceCs.Rgb(), 200, 700, 50, new float[] {1, 0.968f, 0.58f}, 300, 700, 100, new float[] {0.968f, 0.541f, 0.42f}, pdfDoc);
        canvas.paintShading(radial);

        PdfPattern.Shading shading = new PdfPattern.Shading(axial, pdfDoc);
        colorRectangle(canvas, new PatternColor(shading), 150, 420, 126, 126);
        canvas.setFillColorShading(shading);
        canvas.rectangle(300, 420, 126, 126);
        canvas.fillStroke();

        //Close document
        pdfDoc.close();
    }


}
