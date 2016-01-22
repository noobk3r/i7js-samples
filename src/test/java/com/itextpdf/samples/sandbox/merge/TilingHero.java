/**
 * Example written by Bruno Lowagie.
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
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
public class TilingHero extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/merge/tiling_hero.pdf";
    public static final String RESOURCE
            = "./src/test/resources/sandbox/merge/hero.pdf";
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TilingHero().manipulatePdf(DEST);
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
        PdfDocument srcDoc = new PdfDocument(new PdfReader(RESOURCE));

        // TODO No getPageSizeWithRotation
        Rectangle pageSize = getPageSizeWithRotation(srcDoc.getFirstPage());
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
        Rectangle mediaBox = new Rectangle(0, 3 * height, width, height);

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        pdfDoc.setDefaultPageSize(new PageSize(mediaBox));
        PdfFormXObject page = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);
        PdfCanvas canvas;
        for (int i = 0; i < 16; ) {
            canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.addXObject(page, 4, 0, 0, 4, 0, 0);
            i++;
            mediaBox = new Rectangle(
                    (i % 4) * width, (4 - (i / 4)) * height,
                    width, - height);
            pdfDoc.setDefaultPageSize(new PageSize(mediaBox));
        }

        srcDoc.close();
        pdfDoc.close();
    }
}
