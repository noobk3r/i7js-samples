/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SimpleTable4 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table4.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable4().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // What 'was wrong' as text below previously mentioned you can see in SimpleTable4.java of itext5.
        // Here in itext6 there is only one way of adding paragraph to table.
        Table table = new Table(1);
        Paragraph right = new Paragraph("This is right, because we create a paragraph with an indentation to the left and as we are adding the paragraph in composite mode, all the properties of the paragraph are preserved.");
        right.setMarginLeft(20);
        Cell rightCell = new Cell().add(right);
        table.addCell(rightCell);
        doc.add(table);

        doc.close();
    }
}
