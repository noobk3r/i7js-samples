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
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SimpleTable11 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table11.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable11().manipulatePdf(DEST);
    }

    public Cell createCell(String content, float borderWidth, int colspan, Property.TextAlignment alignment) {
        Cell cell = new Cell(1, colspan).add(new Paragraph(content));
        cell.setTextAlignment(alignment);
        cell.setBorder(new SolidBorder(borderWidth));
        return cell;
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{1, 2, 1, 1, 1});
        table.setWidthPercent(100);
        table.addCell(createCell("SKU", 2, 1, Property.TextAlignment.LEFT));
        table.addCell(createCell("Description", 2, 1, Property.TextAlignment.LEFT));
        table.addCell(createCell("Unit Price", 2, 1, Property.TextAlignment.LEFT));
        table.addCell(createCell("Quantity", 2, 1, Property.TextAlignment.LEFT));
        table.addCell(createCell("Extension", 2, 1, Property.TextAlignment.LEFT));
        String[][] data = {
                {"ABC123", "The descriptive text may be more than one line and the text should wrap automatically",
                        "$5.00", "10", "$50.00"},
                {"QRS557", "Another description", "$100.00", "15", "$1,500.00"},
                {"XYZ999", "Some stuff", "$1.00", "2", "$2.00"}
        };
        for (String[] row : data) {
            table.addCell(createCell(row[0], 1, 1, Property.TextAlignment.LEFT));
            table.addCell(createCell(row[1], 1, 1, Property.TextAlignment.LEFT));
            table.addCell(createCell(row[2], 1, 1, Property.TextAlignment.RIGHT));
            table.addCell(createCell(row[3], 1, 1, Property.TextAlignment.RIGHT));
            table.addCell(createCell(row[4], 1, 1, Property.TextAlignment.RIGHT));
        }
        table.addCell(createCell("Totals", 2, 4, Property.TextAlignment.LEFT));
        table.addCell(createCell("$1,552.00", 2, 1, Property.TextAlignment.RIGHT));
        doc.add(table);

        doc.close();
    }
}
