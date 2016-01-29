/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/22382717/write-two-itext-paragraphs-on-the-same-position
 *
 * We create a Chunk and add a background color.
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.ParagraphRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class UnderlineWithDottedLine extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/underline_with_dotted_line.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new UnderlineWithDottedLine().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("This line will be underlined with a dotted line.");
        p.setNextRenderer(new UnderlinedParagraphRenderer(p));
        // TODO No DottedLineSeparator
        // DottedLineSeparator dottedline = new DottedLineSeparator();
        // dottedline.setOffset(-2);
        // dottedline.setGap(2f);
        // p.add(dottedline);
        doc.add(p);

        doc.close();
    }


    protected class UnderlinedParagraphRenderer extends ParagraphRenderer {
        public UnderlinedParagraphRenderer(Paragraph modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            PdfCanvas canvas = drawContext.getCanvas();
            Rectangle rect = getOccupiedAreaBBox();
            canvas
                    .saveState()
                    .setLineDash(0, 4, 4 / 2)
                    .setLineCapStyle(PdfCanvasConstants.LineCapStyle.ROUND)
                    .moveTo(rect.getLeft(), rect.getBottom())
                    .lineTo(rect.getRight(), rect.getBottom())
                    .stroke()
                    .restoreState();
        }
    }
}
