package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.PageBreak;
import com.itextpdf.model.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_05_21_MovieCountries2 {

    static public final String DEST = "./target/test/resources/Listing_05_21_MovieCountries2.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_05_21_MovieCountries2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, PdfException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addEventHandler(PdfDocumentEvent.EndPage, new Watermarks());
        Document doc = new Document(pdfDoc);

        //Add element, create new page, add element
        doc.add(new Paragraph("Hello")).add(new PageBreak()).add(new Paragraph("World"));

        //Close document
        doc.close();
    }

    public static class Watermarks implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNum(page);
            try {
                PdfCanvas canvas = new PdfCanvas(page);
                //Watermark to be added here.
                canvas.release();
            } catch (PdfException e) {
                e.printStackTrace();
            }
        }
    }


}
