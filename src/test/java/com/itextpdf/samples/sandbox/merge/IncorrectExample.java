/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/28332692/itextsharp-rotated-pdf-page-reverts-orientation-when-file-is-rasterized-at-print
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;

// TODO compare the fifth page with itext5 result to see the difference
@Category(SampleTest.class)
public class IncorrectExample extends GenericTest {
    public static final String DEST
            = "./target/test/resources/sandbox/merge/incorrect_example.pdf";
    public static final String SOURCE
            = "./src/test/resources/sandbox/merge/pages.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new IncorrectExample().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SOURCE));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        Rectangle pageSize;
        PdfCanvas canvas;
        PdfFormXObject page;
        for (int i = 1; i <= srcDoc.getNumberOfPages(); i++) {
            pageSize = getPageSize(srcDoc, i);
            pdfDoc.setDefaultPageSize(new PageSize(pageSize));
            canvas = new PdfCanvas(pdfDoc.addNewPage());
            page = srcDoc.getPage(i).copyAsFormXObject(pdfDoc);
            if (isPortrait(srcDoc, i)) {
                canvas.addXObject(page, 0, 0);
            } else {
                canvas.addXObject(page, 0, 1, -1, 0, pageSize.getWidth(), 0);
            }

        }
        pdfDoc.close();
        srcDoc.close();
    }

    public Rectangle getPageSize(PdfDocument pdfDoc, int pageNumber) {
        Rectangle pageSize = pdfDoc.getPage(pageNumber).getPageSize();
        return new Rectangle(
                Math.min(pageSize.getWidth(), pageSize.getHeight()),
                Math.max(pageSize.getWidth(), pageSize.getHeight()));
    }

    public boolean isPortrait(PdfDocument pdfDoc, int pageNumber) {
        Rectangle pageSize = pdfDoc.getPage(pageNumber).getPageSize();
        return pageSize.getHeight() > pageSize.getWidth();
    }
}
