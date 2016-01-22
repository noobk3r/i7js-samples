/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/26853894/continue-field-output-on-second-page-with-itextsharp
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class AddExtraPage extends GenericTest {
    public static String SRC = "./src/test/resources/sandbox/acroforms/stationery.pdf";
    public static String DEST = "./target/test/resources/sandbox/acroforms/add_extra_page.pdf";

    public static void main(String[] args) throws Exception {
        new AddExtraPage().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(SRC);
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);

        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC));
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE,
                new PageEventHandler(srcDoc.getFirstPage().copyAsFormXObject(pdfDoc)));
        srcDoc.close();

        Paragraph p = new Paragraph();
        p.add(new Text("Hello "));
        p.add(new Text("World")
                .setFont(new PdfType1Font((Type1Font) FontFactory.createFont(FontConstants.HELVETICA)))
                .setFontSize(12)
                .setBold());
        // TODO Returns empty form, but the form has felds!
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        // PdfArray rectArray = form.getField("body").getWidgets().get(0).getRectangle();

        for (int i = 1; i < 101; i++) {
            doc.add(new Paragraph("Hello " + i));
            doc.add(p);
        }

        form.flatFields();
        pdfDoc.close();

    }


    public static class PageEventHandler implements IEventHandler {
        protected PdfFormXObject xObject;

        public PageEventHandler(PdfFormXObject xObject) {
            this.xObject = xObject;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            if (1 != pdfDoc.getPageNumber(docEvent.getPage())) {

                PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
                canvas.addXObject(xObject, 0, 0);
            }
        }
    }
}
