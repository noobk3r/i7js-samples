/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27906725/itext-placement-of-phrase-within-columntext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ColumnTextAscender extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/column_text_ascender.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColumnTextAscender().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);
        // for canvas usage one should create a page
        pdfDoc.addNewPage();
        Rectangle rect = new Rectangle(50, 750, 200, 50);
        addColumn(doc, rect, false);
        rect = new Rectangle(300, 750, 200, 50);
        addColumn(doc, rect, true);
        doc.close();
    }

    public void addColumn(Document doc, final Rectangle rect, boolean useAscender) {
        PdfCanvas canvas = new PdfCanvas(doc.getPdfDocument().getFirstPage());
        canvas
                .setLineWidth(0.5f)
                .setStrokeColor(Color.RED)
                .rectangle(rect)
                .stroke();
        Paragraph p = new Paragraph("This text is added at the top of the column.");
        // TODO No setUseAscender(boolean)
        p.setNextRenderer(new ParagraphRenderer(p) {
            @Override
            public LayoutResult layout(LayoutContext layoutContext) {
                return super.layout(new LayoutContext(new LayoutArea(layoutContext.getArea().getPageNumber(), rect)));
            }
        });
        doc.add(p);
    }
}
