package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.renderer.DocumentRenderer;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Example demonstrates how to build complex layouts using layout manager
 */
public class Listing_99_02_ComplexDocumentLayout {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException, PdfException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Set up renderer. The layout consist of 2 vertical stripes.
        doc.setRenderer(new DocumentRenderer(doc) {
            int nextAreaNumber = 0;
            int currentPageNumber;

            @Override
            public LayoutArea getNextArea() {
                if (nextAreaNumber % 2 == 0) {
                    currentPageNumber = super.getNextArea().getPageNumber();
                    nextAreaNumber++;
                    return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(100, 100, 100, 500)));
                } else {
                    nextAreaNumber++;
                    return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(400, 100, 100, 500)));
                }
            }
        });

        //Create paragraph and place it to layout
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            text.append("A very long text is here...");
        }
        doc.add(new Paragraph(text.toString()).setFont(new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""))));

        //Close document
        doc.close();

    }

}
