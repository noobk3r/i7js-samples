/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/22122340/creating-table-with-2-rows-in-pdf-footer-using-itext
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TableFooter extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/table_footer.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableFooter().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(36, 36, 72, 36);

        Table table = new Table(1);
        table.setWidth(523);

        Cell cell = new Cell().add(new Paragraph("This is a test doc"));
        cell.setBackgroundColor(Color.ORANGE);
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("This is a copyright notice"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);

        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new TableFooterEventHandler(table));

        for (int i = 0; i < 150; i++) {
            doc.add(new Paragraph("Hello World!"));
        }
        doc.add(new AreaBreak());
        doc.add(new Paragraph("Hello World!"));
        doc.add(new AreaBreak());
        doc.add(new Paragraph("Hello World!"));

        doc.close();
    }


    protected class TableFooterEventHandler implements IEventHandler {
        private Table table;

        public TableFooterEventHandler(Table table) {
            this.table = table;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            // TODO Get rid of magic numbers (cannot get margins from PdfDocument)
            new Canvas(canvas, pdfDoc, new Rectangle(36, 36, page.getPageSize().getWidth() - 72, 100))
                    .add(table);
        }
    }
}
