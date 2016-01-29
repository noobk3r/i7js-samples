/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/25749828/how-to-draw-border-for-whole-pdf-pages-using-itext-library-5-5-2
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
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
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class PageBorder extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/page_border.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PageBorder().manipulatePdf(DEST);
    }

    public static List<Integer> getFactors(int n) {
        List<Integer> factors = new ArrayList<>();
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
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, new PageBorderEventHandler());
        Document doc = new Document(pdfDoc);
        List<Integer> factors;
        for (int i = 2; i < 301; i++) {
            factors = getFactors(i);
            if (factors.size() == 1) {
                doc.add(new Paragraph("This is a prime number!"));
            }
            for (int factor : factors) {
                doc.add(new Paragraph("Factor: " + factor));
            }
            if (300 != i) {
                doc.add(new AreaBreak());
            }
        }
        doc.close();
    }


    public static class PageBorderEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;

            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle rect = docEvent.getPage().getPageSize();

            canvas.setLineWidth(5).setStrokeColor(Color.RED).rectangle(rect).stroke();
        }
    }
}
