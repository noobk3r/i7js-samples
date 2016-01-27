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
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class RepeatLastRows extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/repeat_last_rows.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RepeatLastRows().manipulatePdf(DEST);
    }


    private class RepeatTableRenderer extends TableRenderer {
        public RepeatTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        protected RepeatTableRenderer(Table modelElement) {
            super(modelElement);
        }

        @Override
        public RepeatTableRenderer getNextRenderer() {
            return new RepeatTableRenderer((Table) modelElement);
        }

        @Override
        protected TableRenderer[] split(int row) {
            RepeatTableRenderer splitRenderer = createSplitRenderer(
                    new Table.RowRange(rowRange.getStartRow(), rowRange.getStartRow() + row));
            splitRenderer.rows = rows.subList(0, row);
            RepeatTableRenderer overflowRenderer;
            if (rows.size() - row > 5) {
                overflowRenderer = createOverflowRenderer(
                        new Table.RowRange(rowRange.getStartRow() + row, rowRange.getFinishRow()));
                overflowRenderer.rows = rows.subList(row, rows.size());
            } else {
                overflowRenderer = createOverflowRenderer(
                        new Table.RowRange(rowRange.getFinishRow() - 5, rowRange.getFinishRow()));
                overflowRenderer.rows = rows.subList(rows.size() - 5, rows.size());
            }
            splitRenderer.occupiedArea = occupiedArea;
            return new TableRenderer[]{splitRenderer, overflowRenderer};
        }
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        table.setWidth(523);
        table.setNextRenderer(new RepeatTableRenderer(table, new Table.RowRange(0, 113)));
        // the number is changed in order to provide the same as in itext5 example
        for (int i = 1; i < 115; i++)
            table.addCell(new Cell().add(new Paragraph("row " + i)));
        doc.add(table);

        doc.close();
    }

}
