/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/28554413/how-to-add-overlay-text-with-link-annotations-to-existing-pdf
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
@Ignore
public class AddLinkAnnotation4 extends GenericTest {
    //    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_link_annotation4.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLinkAnnotation4().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        PdfFont bold = PdfFontFactory.createFont(FontFactory.createFont(FontConstants.HELVETICA_BOLD));
        Link link = new Link("The Best iText Questions on StackOverflow",
                PdfAction.createURI("http://pages.itextpdf.com/ebook-stackoverflow-questions.html"));
        link.setFont(bold).setFontSize(12);
        Paragraph p = new Paragraph("Download ");
        p.add(link);
        p.add(" and discover \nmore than 200 questions and answers.");
        // TODO LinkAnnotation did not rotate with the paragraph 
        new Document(pdfDoc).showTextAligned(p, 30, 600, 1, Property.TextAlignment.LEFT,
                Property.VerticalAlignment.TOP, (float) Math.PI / 2);
        pdfDoc.close();
    }
}
