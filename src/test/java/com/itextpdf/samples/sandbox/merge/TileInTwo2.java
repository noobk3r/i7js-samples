/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/*
 * Example written by Bruno Lowagie in answer to a question on StackOverflow:
 * http://stackoverflow.com/questions/27011829/divide-one-page-pdf-file-in-two-pages-pdf-file
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TileInTwo2 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/merge/tile_in_two2.pdf";
    public static final String SRC
            = "./src/test/resources/sandbox/merge/united_states.pdf";
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TileInTwo2().manipulatePdf(DEST);
    }

    public static Rectangle getHalfPageSize(Rectangle pageSize) {
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
        return new Rectangle(width / 2, height);
    }

    /**
     * Gets the rotated page from a page dictionary.
     * @param page the page
     * @return the rotated page rectangle
     */
    public static Rectangle getPageSizeWithRotation(final PdfPage page) {
        Rectangle rect = page.getPageSize();
        int rotation = page.getRotation();
        while (rotation > 0) {
            rect = new Rectangle(rect.getHeight(), rect.getWidth());
            rotation -= 90;
        }
        return rect;
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC));
        // TODO No getPageSizeWithRotation
        Rectangle mediaBox = new Rectangle(getHalfPageSize(getPageSizeWithRotation(srcDoc.getFirstPage())));

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        pdfDoc.setDefaultPageSize(new PageSize(mediaBox));

        PdfCanvas canvas;
        int n = srcDoc.getNumberOfPages();
        int i = 1;
        while (true) {
            PdfFormXObject page = srcDoc.getPage(i).copyAsFormXObject(pdfDoc);
            canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.addXObject(page, 0, 0);
            canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.addXObject(page, -mediaBox.getWidth(), 0);
            //content.addTemplate(page, 0, 0);
            if (++i > n) {
                break;
            }
            // TODO No getPageSizeWithRotation
            mediaBox = new Rectangle(getHalfPageSize(getPageSizeWithRotation(srcDoc.getPage(i))));
            pdfDoc.setDefaultPageSize(new PageSize(mediaBox));
        }
        pdfDoc.close();
        srcDoc.close();
    }
}
