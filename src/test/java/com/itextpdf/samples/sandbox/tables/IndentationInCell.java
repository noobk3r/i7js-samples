/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class IndentationInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/indentation_in_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new IndentationInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        Cell cell;
        cell = new Cell().add("TO:\n\n   name");
        table.addCell(cell);
        cell = new Cell().add("TO:\n\n\u00a0\u00a0\u00a0name");
        table.addCell(cell);
        cell = new Cell();
        cell.add(new Paragraph("TO:"));
        Paragraph p = new Paragraph("name");
        p.setMarginLeft(10);
        cell.add(p);
        table.addCell(cell);
        cell = new Cell();
        cell.add(new Paragraph("TO:"));
        p = new Paragraph("name");
        p.setTextAlignment(Property.TextAlignment.RIGHT);
        cell.add(p);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
