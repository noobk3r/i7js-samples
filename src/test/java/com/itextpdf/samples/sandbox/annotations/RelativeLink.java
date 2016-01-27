/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27063677/use-of-relative-path-for-anchor-method-using-itext-for-pdf-generation
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class RelativeLink extends GenericTest {
    public static final String XML = "./src/test/resources/sandbox/annotations/data.xml";
    public static final String DEST = "./target/test/resources/sandbox/annotations/relative_link.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RelativeLink().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Paragraph chunk = new Paragraph(new Link("Click me", PdfAction.createURI("../../../../." + XML)));
        doc.add(chunk);

        pdfDoc.close();
    }
}
