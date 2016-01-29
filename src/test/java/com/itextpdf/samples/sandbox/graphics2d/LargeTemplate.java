/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.graphics2d;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class LargeTemplate extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/graphics2d/large_template.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new LargeTemplate().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        float width = 602;
        float height = 15872;
        float maxHeight = 5000;
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(width, maxHeight));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfFormXObject template = new PdfFormXObject(new Rectangle(width, height));
        // TODO There is no PdfGraphics2D
        // Graphics2D g2d = new PdfGraphics2D(template, width, height);
        for (int x = 10; x < width; x += 100) {
            for (int y = 10; y < height; y += 100) {
        //        g2d.drawString(String.format("(%s,%s)", x, y), x, y);
            }
        }
        // g2d.dispose();
        int pages = ((int)height / (int)maxHeight) + 1;
        for (int p = 0; p < pages; ) {
            p++;
            canvas.addXObject(template, 0, (p * maxHeight) - height);
            pdfDoc.addNewPage();
        }

        doc.close();
    }
}
