package com.itextpdf.samples.book.chapter05;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Category(SampleTest.class)
public class Listing_05_21_MovieCountries2 extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_05_21_MovieCountries2/Listing_05_21_MovieCountries2.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_05_21_MovieCountries2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addEventHandler(PdfDocumentEvent.EndPage, new Watermarks());
        Document doc = new Document(pdfDoc);

        //Add element, create new page, add element
        doc.add(new Paragraph("Hello")).add(new AreaBreak()).add(new Paragraph("World"));

        //Close document
        doc.close();
    }

    public static class Watermarks implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNum(page);
            PdfCanvas canvas = new PdfCanvas(page);
            //Watermark to be added here.
            canvas.release();
        }
    }


}
