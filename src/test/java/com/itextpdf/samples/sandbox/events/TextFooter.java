/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27780756/adding-footer-with-itext-doesnt-work
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TextFooter extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/text_footer.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TextFooter().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler());
        for (int i = 0; i < 3; ) {
            i++;
            doc.add(new Paragraph("Test " + i));
            if (3 != i) {
                doc.add(new AreaBreak());
            }
        }
        pdfDoc.close();
    }


    protected class TextFooterEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            canvas.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createStandardFont(FontConstants.HELVETICA_OBLIQUE), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO We do not know leftMargin by PdfDocument
            canvas.moveText(pageSize.getWidth() / 2 - 20, pageSize.getTop() - 10);
            canvas.showText("this is a header");
            canvas.moveText(0, -820);
            canvas.showText("this is a footer");
            canvas.endText();
            canvas.release();
        }
    }
}
