/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21871027/rotating-in-itextsharp-while-preserving-comment-location-orientation
 *
 * Example that shows how to scale an existing PDF using the UserUnit and how to remove the rotation of a page.
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ScaleRotate extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/pages.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/scale_rotate.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ScaleRotate().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        int n = pdfDoc.getNumberOfPages();
        PdfDictionary page;
        for (int p = 1; p <= n; p++) {
            page = pdfDoc.getPage(p).getPdfObject();
            if (page.getAsNumber(PdfName.UserUnit) == null)
                page.put(PdfName.UserUnit, new PdfNumber(2.5f));
            page.remove(PdfName.Rotate);
        }
        pdfDoc.close();
    }
}
