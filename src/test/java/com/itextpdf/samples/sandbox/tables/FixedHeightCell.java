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

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FixedHeightCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/fixed_height_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FixedHeightCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(5);
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 5; c++) {
                cell = new Cell();
                cell.add(new Paragraph(String.valueOf((char) r) + String.valueOf(c)));
                if (r == 'D') {
                    // TODO Implement Fixed height:
                    cell.setHeight(60);
                }
                if (r == 'E') {
                    cell.setHeight(60);
                    if (c == 4) {
                        cell.setHeight(120);
                    }
                }
                if (r == 'F') {
                    cell.setHeight(120);
                    cell.setHeight(60);
                    if (c == 2) {
                        cell.add(new Paragraph("This cell has more content than the other cells"));
                    }
                }
                table.addCell(cell);
            }
        }
        doc.add(table);

        doc.close();
    }
}
