/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class TableInColumn extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/losing_row_content.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableInColumn().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        pdfDoc.addNewPage();
        Document doc = new Document(pdfDoc);

        Table table;
        Cell cell = new Cell();
        for (int i = 1; i <= 30; i++)
            cell.add(new Paragraph("Line " + i));
        table = new Table(1);
        // table.setSplitLate(false);
        table.addCell(cell);
        addTable(pdfDoc, table);
        pdfDoc.addNewPage();
        table = new Table(1);
        // table.setSplitLate(false);
        table.addCell(cell);
        table.addCell(cell);
        addTable(pdfDoc, table);

        doc.close();
    }

    public void addTable(PdfDocument pdfDoc, Table table) {
        Rectangle pagedimension = new Rectangle(36, 36, 559, 806);
        drawColumnText(pdfDoc, pagedimension, table, true);
        Rectangle rect;
        // TODO We don't know before rendering size of added text
//        if (ColumnText.hasMoreText(status)) {
        rect = pagedimension;
//        }
//        else {
//            rect = new Rectangle(36, 36, 559, 806 - ((y_position - 36) / 2));
//        }
        drawColumnText(pdfDoc, rect, table, false);
    }

    public void drawColumnText(PdfDocument pdfDoc, Rectangle rect, Table table, boolean simulate) {
        new Document(pdfDoc).add(table
                // TODO setMarginXXX on table causes no effect
                //.setFixedPosition(rect.getLeft(), rect.getBottom(), rect.getWidth()-100));
                .setMarginBottom(rect.getBottom())
                .setPaddingLeft(rect.getLeft())
                .setMarginRight(pdfDoc.getFirstPage().getPageSize().getRight() - rect.getRight())
                .setMarginTop(pdfDoc.getFirstPage().getPageSize().getTop() - rect.getTop()));
    }
}
