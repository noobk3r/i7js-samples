/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

/**
 * Example demonstrates how to build complex layouts using layout manager
 */
@Category(SampleTest.class)
public class Listing_99_02_ComplexDocumentLayout extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_99_02_ComplexDocumentLayout/Listing_99_02_ComplexDocumentLayout.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_99_02_ComplexDocumentLayout().manipulatePdf(DEST);
    }

    @Override
    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Set up renderer. The layout consist of 2 vertical stripes.
        doc.setRenderer(new DocumentRenderer(doc) {
            int nextAreaNumber = 0;
            int currentPageNumber;

            @Override
            public LayoutArea updateCurrentArea(LayoutResult overflowResult) {
                if (nextAreaNumber % 2 == 0) {
                    currentPageNumber = super.updateCurrentArea(overflowResult).getPageNumber();
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
        doc.add(new Paragraph(text.toString()).setFont(PdfFontFactory.createFont(FontConstants.HELVETICA)));

        //Close document
        doc.close();
    }

}
