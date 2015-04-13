package com.itextpdf.samples;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.DeviceRgb;
import com.itextpdf.basics.PdfException;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.PageBreak;
import com.itextpdf.model.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_03_01_FestivalOpening {

    static private final String RESULT = "./result.pdf";
    static private final float pageWidth = PageSize.Default.getWidth();
    static private final float pageHeight = PageSize.Default.getHeight();

    public static void main(String args[]) throws IOException, PdfException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Initialize paragraph, add it to document, add new page, add paragraph again
        Paragraph p = new Paragraph("Foobar Film Festival");
        doc.add(p).add(new PageBreak()).add(p);

        //Initialize canvas and write to it
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getLastPage());
        float sine = (float) Math.sin(Math.PI / 60);
        float cosine = (float) Math.cos(Math.PI / 60);
        canvas.saveState().beginText().setTextRenderingMode(2).
                setLineWidth(1.5f).setFillColor(DeviceRgb.Red).setStrokeColor(DeviceRgb.White).
                setFontAndSize(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, "")), 36).setTextMatrix(cosine, sine, -sine, cosine, 50, 324).
                showText("SOLD OUT").endText().restoreState();

        //Initialize "under" canvas and write to it
        PdfCanvas underCanvas = new PdfCanvas(pdfDoc.getLastPage().newContentStreamBefore(), pdfDoc.getLastPage().getResources());
        underCanvas.saveState().setFillColor(new DeviceRgb(0xFF, 0xD7, 0x00)).
                rectangle(5, 5, pageWidth - 10, pageHeight - 10).fill().restoreState();
        canvas.release();
        //Close document
        doc.close();

    }


}
