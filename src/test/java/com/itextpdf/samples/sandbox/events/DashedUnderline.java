/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29260730/how-do-you-underline-text-with-dashedline-in-itext-pdf
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class DashedUnderline extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/dashed_underline.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DashedUnderline().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("This text is not underlined"));
        Text chunk1 = new Text("This text is underlined with a solid line");
        chunk1.setUnderline(1, -3);
        doc.add(new Paragraph(chunk1));
        Text chunk2 = new Text("This text is underlined with a dashed line");
        chunk2.setNextRenderer(new DashedLineTextRenderer(chunk2));
        doc.add(new Paragraph(chunk2));
        pdfDoc.close();
    }


    protected class DashedLineTextRenderer extends TextRenderer {
        public DashedLineTextRenderer(Text textElement) {
            super(textElement);
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            Rectangle rect = this.getOccupiedAreaBBox();
            PdfCanvas canvas = drawContext.getCanvas();
            canvas.saveState();
            canvas.setLineDash(3, 3);
            canvas.moveTo(rect.getLeft(), rect.getBottom() - 3);
            canvas.lineTo(rect.getRight(), rect.getBottom() - 3);
            canvas.stroke();
            canvas.restoreState();
        }
    }
}
