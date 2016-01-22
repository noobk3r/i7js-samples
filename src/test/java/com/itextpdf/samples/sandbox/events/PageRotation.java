/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/22051835/itext-pdf-document-rotate-some-but-not-all-pages
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class PageRotation extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/page_rotation.pdf";
    /* Constants form itext5 */
    public static final PdfNumber PORTRAIT = new PdfNumber(0);
    public static final PdfNumber LANDSCAPE = new PdfNumber(90);
    public static final PdfNumber INVERTEDPORTRAIT = new PdfNumber(180);
    public static final PdfNumber SEASCAPE = new PdfNumber(270);

    private static final Paragraph helloWorld = new Paragraph("Hello World!");

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PageRotation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        PageRotationEventHandler eventHandler = new PageRotationEventHandler();
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, eventHandler);
        Document doc = new Document(pdfDoc);
        doc.add(helloWorld);
        eventHandler.setRotation(LANDSCAPE);
        doc.add(new AreaBreak());
        doc.add(helloWorld);
        eventHandler.setRotation(INVERTEDPORTRAIT);
        doc.add(new AreaBreak());
        doc.add(helloWorld);
        eventHandler.setRotation(SEASCAPE);
        doc.add(new AreaBreak());
        doc.add(helloWorld);
        eventHandler.setRotation(PORTRAIT);
        doc.add(new AreaBreak());
        doc.add(helloWorld);
        pdfDoc.close();
    }


    protected class PageRotationEventHandler implements IEventHandler {
        protected PdfNumber rotation = PORTRAIT;

        public void setRotation(PdfNumber orientation) {
            this.rotation = orientation;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            docEvent.getPage().put(PdfName.Rotate, rotation);
        }
    }
}
