package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.colors.DeviceGray;
import com.itextpdf.core.exceptions.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfFormXObject;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_03_26_MoviePosters {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException, PdfException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document and add page
        PdfDocument pdfDoc = new PdfDocument(writer);
        PdfPage page = pdfDoc.addNewPage();

        //Initialize form XObject and write to it
        PdfFormXObject xObj = new PdfFormXObject(pdfDoc);
        PdfCanvas celluloid = new PdfCanvas(xObj);
        celluloid.rectangle(8, 8, 579, 68);
        for (float f = 8.25f; f < 581; f += 6.5f) {
            celluloid.roundRectangle(f, 8.5f, 6, 3, 1.5f).roundRectangle(f, 72.5f, 6, 3, 1.5f);
        }
        celluloid.setFillColor(new DeviceGray(0.1f)).eoFill();

        //Flush xObj to output file
        xObj.flush();

        //Add XObjects to page canvas
        PdfCanvas canvas = new PdfCanvas(page.getContentStream(), page.getResources());
        for (int i = 0; i < 10; i++) {
            canvas.addXObject(xObj, 0, i * 84.2f);
        }

        //Close document
        pdfDoc.close();

    }


}
