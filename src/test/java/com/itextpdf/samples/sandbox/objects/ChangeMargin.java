/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30328489/itextsharp-change-margins-no-document-setpagesize
 */
@Category(SampleTest.class)
public class ChangeMargin extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/change_margin.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeMargin().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        float left = 30;
        float right = 30;
        float top = 60;
        float bottom = 0;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(top, right, bottom, left);
        // Add something in order to begin the page, otherwise you will lose the initial margins
        doc.add(new Paragraph("This is a test"));
        doc.setMargins(0, right, bottom, left);
        for (int i = 1; i < 40; i++) {
            doc.add(new Paragraph("This is a test"));
        }
        doc.close();
    }
}
