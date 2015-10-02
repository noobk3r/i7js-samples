/**
 * This example was written by Bruno Lowagie in answer to the following StackOverflow question:
 * http://stackoverflow.com/questions/29775893/watermark-in-a-pdf-with-itext
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.DeviceGray;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddExtraMargin extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/primes.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_extra_margin.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddExtraMargin().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        int n = pdfDoc.getNumOfPages();
        PdfCanvas over;
        PdfDictionary pageDict;
        PdfArray mediaBox;
        float llx, lly, ury;
        // loop over every page
        for (int i = 1; i <= n; i++) {
            pageDict = pdfDoc.getPage(i).getPdfObject();
            mediaBox = pageDict.getAsArray(PdfName.MediaBox);
            llx = mediaBox.getAsFloat(0);
            lly = mediaBox.getAsFloat(1);
            ury = mediaBox.getAsFloat(3);
            mediaBox.set(0, new PdfNumber(llx - 36));
            over = new PdfCanvas(pdfDoc.getPage(i));
            //over = stamper.getOverContent(i);
            over.saveState();
            over.setFillColor(new DeviceGray(0.5f));
            over.rectangle(llx - 36, lly, 36, ury - llx);
            over.fill();
            over.restoreState();
        }
        pdfDoc.close();
    }
}
