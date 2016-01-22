/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/29152313/fix-the-orientation-of-a-pdf-in-order-to-scale-it
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.events.Event;
import com.itextpdf.core.events.IEventHandler;
import com.itextpdf.core.events.PdfDocumentEvent;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ScaleDown extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/scale_down.pdf";
    public static final String SRC = "./src/test/resources/sandbox/events/orientations.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ScaleDown().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        // Create the source document
        PdfDocument srcDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        float scale = 0.5f;
        ScaleDownEventHandler eventHandler = new ScaleDownEventHandler(scale);
        int n = srcDoc.getNumberOfPages();
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, eventHandler);

        for (int p = 1; p <= n; p++) {
            eventHandler.setPageDict(srcDoc.getPage(p).getPdfObject());
            PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            PdfFormXObject page = srcDoc.getPage(p).copyAsFormXObject(pdfDoc);
            canvas.addXObject(page, scale, 0f, 0f, scale, 0f, 0f);
        }
        pdfDoc.close();
    }


    protected class ScaleDownEventHandler implements IEventHandler {
        protected float scale = 1;
        protected PdfDictionary pageDict;

        public ScaleDownEventHandler(float scale) {
            this.scale = scale;
        }

        public void setPageDict(PdfDictionary pageDict) {
            this.pageDict = pageDict;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            page.put(PdfName.Rotate, pageDict.getAsNumber(PdfName.Rotate));
            page.put(PdfName.MediaBox, scaleDown(pageDict.getAsArray(PdfName.MediaBox), scale));
            page.put(PdfName.CropBox, scaleDown(pageDict.getAsArray(PdfName.CropBox), scale));

        }

        protected PdfArray scaleDown(PdfArray original, float scale) {
            if (original == null)
                return null;
            float width = original.getAsFloat(2)
                    - original.getAsFloat(0);
            float height = original.getAsFloat(3)
                    - original.getAsFloat(1);
            PdfArray result = new PdfArray();
            result.add(new PdfNumber(0));
            result.add(new PdfNumber(0));
            result.add(new PdfNumber(width * scale));
            result.add(new PdfNumber(height * scale));
            return result;
        }
    }
}
