/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21628429/itextsharp-how-to-generate-a-report-with-dynamic-header-in-pdf-using-itextsharp
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class VariableHeader extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/variable_header.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new VariableHeader().manipulatePdf(DEST);
    }

    public static List<Integer> getFactors(int n) {
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        VariableHeaderEventHandler handler = new VariableHeaderEventHandler();
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
        List<Integer> factors;
        for (int i = 2; i < 301; i++) {
            factors = getFactors(i);
            if (factors.size() == 1) {
                doc.add(new Paragraph("This is a prime number!"));
            }
            for (int factor : factors) {
                doc.add(new Paragraph("Factor: " + factor));
            }

            handler.setHeader(String.format("THE FACTORS OF %s", i));
            if (300 != i) {
                doc.add(new AreaBreak());
            }
        }
        pdfDoc.close();
    }


    protected class VariableHeaderEventHandler implements IEventHandler {
        protected String header;

        public void setHeader(String header) {
            this.header = header;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent documentEvent = (PdfDocumentEvent) event;
            try {
                new PdfCanvas(documentEvent.getPage())
                        .beginText()
                        .setFontAndSize(PdfFont.createStandardFont(documentEvent.getDocument(),
                                FontConstants.HELVETICA), 12)
                        .moveText(450, 806)
                        .showText(header)
                        .endText()
                        .stroke();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
