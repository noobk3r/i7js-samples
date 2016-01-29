/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/31314993/how-to-set-a-default-zoom-of-100-using-itext-java
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class OpenAt100pct extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/annotations/open_at_100pct.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new OpenAt100pct().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc, new PageSize(612, 792));
        doc.add(new Paragraph("Hello World"));
        pdfDoc.getCatalog().setOpenAction(PdfExplicitDestination.createXYZ(1, 0, 842, 1));
        doc.close();
    }
}
