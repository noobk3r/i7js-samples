/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written in answer to:
 * http://stackoverflow.com/questions/33582245/extract-pdf-page-and-insert-into-existing-pdf
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SuperImpose extends GenericTest {
    public static final String SRC =
            "./src/test/resources/sandbox/stamper/primes.pdf";
    public static final String[] EXTRA = {
            "./src/test/resources/sandbox/stamper/hello.pdf",
            "./src/test/resources/sandbox/stamper/base_url.pdf",
            "./src/test/resources/sandbox/stamper/state.pdf"
    };
    public static final String DEST =
            "./target/test/resources/sandbox/stamper/super_impose.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SuperImpose().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
        PdfDocument srcDoc;
        PdfFormXObject page;
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage().newContentStreamBefore(),
                pdfDoc.getFirstPage().getResources(), pdfDoc);
        for (String path : EXTRA) {
            srcDoc = new PdfDocument(new PdfReader(path));
            page = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);
            canvas.addXObject(page, 0, 0);
            srcDoc.close();
        }
        pdfDoc.close();
    }
}
