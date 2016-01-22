/**
 * This question was answered by Bruno Lowagie in answer to the following question on StackOverflow:
 * http://stackoverflow.com/questions/30053684/how-to-add-border-to-paragraph-in-itext-pdf-library-in-java
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.ParagraphRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class BorderForParagraph extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/border_for_paragraph.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new BorderForParagraph().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Hello,"));
        doc.add(new Paragraph("In this doc, we'll add several paragraphs that will trigger page events. " +
                "As long as the event isn't activated, nothing special happens, " +
                "but let's make the event active and see what happens:"));
        Paragraph renderedParagraph = new Paragraph("This paragraph now has a border. Isn't that fantastic? " +
                "By changing the event, we can even provide a background color, " +
                "change the line width of the border and many other things. Now let's deactivate the event.");
        renderedParagraph.setNextRenderer(new BorderParagraphRenderer(renderedParagraph));
        doc.add(renderedParagraph);
        doc.add(new Paragraph("This paragraph no longer has a border."));
        pdfDoc.close();
    }


    private class BorderParagraphRenderer extends ParagraphRenderer {
        public BorderParagraphRenderer(Paragraph modelElement) {
            super(modelElement);
        }

        @Override
        public void drawBorder(DrawContext drawContext) {
            super.drawBorder(drawContext);
            Rectangle rect = getOccupiedAreaBBox();
            drawContext.getCanvas().rectangle(rect.getLeft(), rect.getBottom(),
                    rect.getWidth(), rect.getHeight());
            drawContext.getCanvas().stroke();
        }
    }
}
