/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
* Example written by Bruno Lowagie in answer to:
* http://stackoverflow.com/questions/35449606
*/
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TableHeader extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/table_header.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableHeader().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        TableHeaderEventHandler handler = new TableHeaderEventHandler();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(20 + handler.getTableHeight(), 36, 36, 36);
        for (int i = 0; i < 50; i++) {
            doc.add(new Paragraph("Hello World!"));
        }
        doc.add(new AreaBreak());
        doc.add(new Paragraph("Hello World!"));
        doc.add(new AreaBreak());
        doc.add(new Paragraph("Hello World!"));
        doc.close();

    }


    public class TableHeaderEventHandler implements IEventHandler {
        protected Table table;
        protected float tableHeight;
        public TableHeaderEventHandler() {
            table = new Table(1);
            table.setWidth(523);
            table.addCell("Header row 1");
            table.addCell("Header row 2");
            table.addCell("Header row 3");
            TableRenderer renderer = table.createRendererSubTree();
            renderer.setParent(new Document(new PdfDocument(new PdfWriter(new ByteArrayOutputStream()))).getRenderer());
            tableHeight = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4))).getOccupiedArea().getBBox().getHeight();
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            // TODO Get rid of magic numbers (cannot get margins from PdfDocument)
            new Canvas(canvas, pdfDoc, new Rectangle(36,
                    pdfDoc.getDefaultPageSize().getTop() - getTableHeight() - 20,
                    100, getTableHeight()))
                    .add(table);
        }

        public float getTableHeight() {
            return tableHeight;
        }
    }
}
