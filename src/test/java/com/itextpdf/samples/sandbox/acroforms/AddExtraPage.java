/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/26853894/continue-field-output-on-second-page-with-itextsharp
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddExtraPage extends GenericTest {
    public static String SRC = "./src/test/resources/sandbox/acroforms/stationery.pdf";
    public static String DEST = "./target/test/resources/sandbox/acroforms/add_extra_page.pdf";

    public static void main(String[] args) throws Exception {
        new AddExtraPage().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC));
        PdfAcroForm form = PdfAcroForm.getAcroForm(srcDoc, false);
        final Rectangle rect = form.getField("body").getWidgets().get(0).getRectangle().toRectangle();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
                new PaginationEventHandler(srcDoc.getFirstPage().copyAsFormXObject(pdfDoc)));
        srcDoc.close();

        doc.setRenderer(new DocumentRenderer(doc) {
            @Override
            protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
                currentPageNumber = super.updateCurrentArea(overflowResult).getPageNumber();
                // Notice that each time we need to pass a new rectangle object, not the same
                return (currentArea = new LayoutArea(currentPageNumber, rect.clone()));
            }
        });

        Paragraph p = new Paragraph();
        p.add(new Text("Hello "));
        p.add(new Text("World")
                .setFont(PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD)));

        for (int i = 1; i < 101; i++) {
            doc.add(new Paragraph("Hello " + i));
            doc.add(p);
        }

        doc.close();
    }


    protected class PaginationEventHandler implements IEventHandler {
        PdfFormXObject background;

        public PaginationEventHandler(PdfFormXObject background) throws IOException {
            this.background = background;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocument pdfDoc = ((PdfDocumentEvent) event).getDocument();
            int pageNum = pdfDoc.getPageNumber(((PdfDocumentEvent) event).getPage());
            // Add the background
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(pageNum).newContentStreamBefore(),
                    pdfDoc.getPage(pageNum).getResources(), pdfDoc)
                    .addXObject(background, 0, 0);
        }
    }
}
