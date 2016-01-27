/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/29152313/fix-the-orientation-of-a-pdf-in-order-to-scale-it
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
public class Seascape extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/seascape.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Seascape().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, new SeascapeEventHandler());
        for (int i = 0; i < 50; i++)
            doc.add(new Paragraph("Hello World!"));
        doc.add(new AreaBreak());
        doc.add(new Paragraph("Hello World!"));
        doc.add(new AreaBreak());
        doc.add(new Paragraph("Hello World!"));
        pdfDoc.close();
    }


    protected class SeascapeEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent documentEvent = (PdfDocumentEvent) event;
            documentEvent.getPage().getPdfObject().put(PdfName.Rotate, new PdfNumber(270));
        }
    }
}
