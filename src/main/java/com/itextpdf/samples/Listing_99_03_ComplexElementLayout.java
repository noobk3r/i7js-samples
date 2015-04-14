package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutContext;
import com.itextpdf.model.renderer.BlockRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Listing_99_03_ComplexElementLayout {

    static private final String RESULT = "./result.pdf";


    public static void main(String args[]) throws IOException, PdfException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
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
        twoColumnParagraph.setRenderer(new TwoColumnParagraphRenderer(twoColumnParagraph));
        twoColumnParagraph.add(text.toString());
        doc.add(twoColumnParagraph.setFont(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""))));

        doc.add(new Paragraph("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

        //Close document
        doc.close();

    }

    static private class TwoColumnParagraphRenderer extends BlockRenderer {

        public TwoColumnParagraphRenderer(Paragraph modelElement) {
            super(modelElement);
        }

        @Override
        public List<LayoutArea> initElementAreas(LayoutContext context) {
            LayoutArea area = context.getArea();
            List<LayoutArea> areas = new ArrayList<LayoutArea>();
            LayoutArea firstArea = area.clone();
            LayoutArea secondArea = area.clone();
            firstArea.getBBox().setWidth(firstArea.getBBox().getWidth() / 2);
            secondArea.getBBox().setX(secondArea.getBBox().getX() + secondArea.getBBox().getWidth() / 2);
            secondArea.getBBox().setWidth(firstArea.getBBox().getWidth());
            areas.add(firstArea);
            areas.add(secondArea);
            return areas;
        }

        @Override
        protected BlockRenderer createSplitRenderer() {
            return new TwoColumnParagraphRenderer((Paragraph) modelElement);
        }

        @Override
        protected BlockRenderer createOverflowRenderer() {
            return new TwoColumnParagraphRenderer((Paragraph) modelElement);
        }
    }

}
