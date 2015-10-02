package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class NestedTables extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/nested_tables.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NestedTables().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        float[] columnWidths = {183, 31, 88, 49, 35, 25, 35, 35, 35, 32, 32, 33, 35, 60, 46, 26};
        Table table = new Table(columnWidths);
        table.setWidth(770F);
        buildNestedTables(table);
        doc.add(new Paragraph("Add table straight to another table"));
        doc.add(table);

        // IMPORTANT!!!
        // Two other examples (with methods buildNestedTables1 and buildNestedTables2)
        // make no sense in itext6 because there is only one way of adding cells to tables
        // in itext6. Please, check NestedTables.java in itext5.

        doc.close();
    }

    private void buildNestedTables(Table outerTable) {
        Table innerTable1 = new Table(1);
        Table innerTable2 = new Table(2);
        Cell cell;
        innerTable1.addCell(new Cell().add(new Paragraph("Cell 1")));
        innerTable1.addCell(new Cell().add(new Paragraph("Cell 2")));
        outerTable.addCell(new Cell().add(innerTable1));
        innerTable2.addCell(new Cell().add(new Paragraph("Cell 3")));
        innerTable2.addCell(new Cell().add(new Paragraph("Cell 4")));
        outerTable.addCell(new Cell().add(innerTable2));
        cell = new Cell(1, 14);
        outerTable.addCell(cell);
    }
}
