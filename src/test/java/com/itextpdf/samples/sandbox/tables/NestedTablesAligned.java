/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class NestedTablesAligned extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/nested_tables_aligned.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NestedTablesAligned().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        float[] columnWidths = {200f, 200f, 200f};
        Table table = new Table(columnWidths);
        buildNestedTables(table);
        doc.add(table);

        doc.close();
    }

    private void buildNestedTables(Table outerTable) {
        Table innerTable1 = new Table(1);
        innerTable1.setWidth(100f);
        innerTable1.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        innerTable1.addCell("Cell 1");
        innerTable1.addCell("Cell 2");
        outerTable.addCell(innerTable1); // add(new Paragraph(innerTable))
        Table innerTable2 = new Table(2);
        innerTable2.setWidth(100f);
        innerTable2.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        innerTable2.addCell("Cell 3");
        innerTable2.addCell("Cell 4");
        outerTable.addCell(innerTable2);
        Table innerTable3 = new Table(2);
        innerTable3.setWidth(100f);
        innerTable3.setHorizontalAlignment(Property.HorizontalAlignment.RIGHT);
        innerTable3.addCell("Cell 5");
        innerTable3.addCell("Cell 6");
        outerTable.addCell(innerTable3);
    }
}