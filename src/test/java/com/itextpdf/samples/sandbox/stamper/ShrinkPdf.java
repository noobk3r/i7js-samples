/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/25356302/shrink-pdf-pages-with-rotation-using-rectangle-in-existing-pdf
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfResources;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ShrinkPdf extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hero.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/shrink_pdf.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ShrinkPdf().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        int n = pdfDoc.getNumberOfPages();
        PdfDictionary page;
        PdfArray crop;
        PdfArray media;
        for (int p = 1; p <= n; p++) {
            page = pdfDoc.getPage(p).getPdfObject();
            media = page.getAsArray(PdfName.CropBox);
            if (media == null) {
                media = page.getAsArray(PdfName.MediaBox);
            }
            crop = new PdfArray();
            crop.add(new PdfNumber(0));
            crop.add(new PdfNumber(0));
            crop.add(new PdfNumber(media.getAsFloat(2) / 2));
            crop.add(new PdfNumber(media.getAsFloat(3) / 2));
            page.put(PdfName.MediaBox, crop);
            page.put(PdfName.CropBox, crop);
            new PdfCanvas(pdfDoc.getPage(p).newContentStreamBefore(),
                    new PdfResources(), pdfDoc).writeLiteral("\nq 0.5 0 0 0.5 0 0 cm\nq\n");
            new PdfCanvas(pdfDoc.getPage(p).newContentStreamAfter(),
                    new PdfResources(), pdfDoc).writeLiteral("\nQ\nQ\n");
        }
        pdfDoc.close();
    }
}
