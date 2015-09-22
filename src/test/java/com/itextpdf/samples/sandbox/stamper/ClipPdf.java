/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/26773942/itext-crop-out-a-part-of-pdf-file
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

@Category(SampleTest.class)
public class ClipPdf extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hero.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/clip_pdf.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ClipPdf().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        int n = pdfDoc.getNumOfPages();
        PdfDictionary page;
        PdfArray media;
        for (int p = 1; p <= n; p++) {
            page = pdfDoc.getPage(p).getPdfObject();
            media = page.getAsArray(PdfName.CropBox);
            if (media == null) {
                media = page.getAsArray(PdfName.MediaBox);
            }
            float llx = media.getAsFloat(0) + 200;
            float lly = media.getAsFloat(1) + 200;
            float w = media.getAsFloat(2) - media.getAsFloat(0) - 400;
            float h = media.getAsFloat(3) - media.getAsFloat(1) - 400;
            // !IMPORTANT to write Locale
            String command = String.format(
                    Locale.ENGLISH,
                    "\nq %.2f %.2f %.2f %.2f re W n\nq\n",
                    llx, lly, w, h);
            new PdfCanvas(pdfDoc.getPage(p).newContentStreamBefore(), new PdfResources(), pdfDoc).writeLiteral(command);
            new PdfCanvas(pdfDoc.getPage(p).newContentStreamAfter(), new PdfResources(), pdfDoc).writeLiteral("\nQ\nQ\n");
        }
        pdfDoc.close();
    }
}
