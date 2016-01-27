/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class CenterColumnVertically extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/center_column_vertically.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CenterColumnVertically().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        float llx = 50;
        float lly = 650;
        float urx = 400;
        float ury = 800;
        Rectangle rect = new Rectangle(llx, lly, urx, ury);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.setStrokeColor(Color.RED).setLineWidth(0.5f).rectangle(rect).stroke();
        Paragraph p = new Paragraph("This text is centered vertically. " +
                "It is rendered in the middle of the red rectangle.");
        p.setFixedLeading(12f);
        // TODO Since we don't know paragraphs' positions before rendering, we can't center text (feels the absence of SimpleColumn)
        // rect = new Rectangle(llx, lly, urx-llx, ury - (lly / 2));
        drawColumnText(pdfDoc, rect, p);

        doc.close();
    }

    public void drawColumnText(PdfDocument pdfDoc, Rectangle rect, Paragraph p) {
        new Document(pdfDoc).add(p.setWidth(rect.getWidth()).setHeight(rect.getHeight()).setMarginLeft(rect.getLeft()));
    }
}
