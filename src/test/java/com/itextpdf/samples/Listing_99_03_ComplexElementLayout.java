package com.itextpdf.samples;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.renderer.ParagraphRenderer;

import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Category(SampleTest.class)
public class Listing_99_03_ComplexElementLayout extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_99_03_ComplexElementLayout.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_99_03_ComplexElementLayout().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            text.append("A very long text is here...");
        }
        Paragraph twoColumnParagraph = new Paragraph();
        twoColumnParagraph.setNextRenderer(new TwoColumnParagraphRenderer(twoColumnParagraph));
        twoColumnParagraph.add(text.toString());
        doc.add(twoColumnParagraph.setFont(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""))));

        doc.add(new Paragraph("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

        //Close document
        doc.close();
    }

    static private class TwoColumnParagraphRenderer extends ParagraphRenderer {

        public TwoColumnParagraphRenderer(Paragraph modelElement) {
            super(modelElement);
        }

        @Override
        public List<Rectangle> initElementAreas(LayoutArea area) {
            List<Rectangle> areas = new ArrayList<Rectangle>();
            Rectangle firstArea = area.getBBox().clone();
            Rectangle secondArea = area.getBBox().clone();
            firstArea.setWidth(firstArea.getWidth() / 2 - 5);
            secondArea.setX(secondArea.getX() + secondArea.getWidth() / 2 + 5);
            secondArea.setWidth(firstArea.getWidth());
            areas.add(firstArea);
            areas.add(secondArea);
            return areas;
        }

        @Override
        protected ParagraphRenderer createSplitRenderer() {
            return new TwoColumnParagraphRenderer((Paragraph) modelElement);
        }

        @Override
        protected ParagraphRenderer createOverflowRenderer() {
            return new TwoColumnParagraphRenderer((Paragraph) modelElement);
        }
    }

}
