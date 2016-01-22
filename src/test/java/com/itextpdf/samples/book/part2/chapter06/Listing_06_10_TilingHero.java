package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_10_TilingHero extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter06/Listing_06_10_TilingHero.pdf";
    public static final String SOURCE = "./src/test/resources/book/part2/chapter06/hero.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_06_09_NUpTool().manipulatePdf(DEST);
    }

    /**
     * Gets the rotated page from a page dictionary.
     * @param page the page
     * @return the rotated  rectangle
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

    public void manipulatePdf(String dest) throws IOException, SQLException {
        PdfReader reader = new PdfReader(SOURCE);
        PdfDocument srcDoc = new PdfDocument(reader);
        // TODO get Page size with rotation
        Rectangle pageSize = getPageSizeWithRotation(srcDoc.getFirstPage());

        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(pageSize));
        pdfDoc.addNewPage();

        // adding the same page 16 times with a different offset
        float x, y;
        for (int i = 0; i < 16; i++) {
            x = -pageSize.getWidth() * (i % 4);
            y = pageSize.getHeight() * (i / 4 - 3);
            // TODO The size of this example result is much bigger than in itext5
            PdfFormXObject page = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);
            new PdfCanvas(pdfDoc.getLastPage())
                    .addXObject(page, 4, 0, 0, 4, x, y);
            if (15 != i) {
                doc.add(new AreaBreak());
            }
        }

        doc.close();
        srcDoc.close();
    }
}
