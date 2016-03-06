/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
* Example written by Bruno Lowagie in answer to the following question:
*  http://stackoverflow.com/questions/35356847
*/
package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

// TODO See the results
@Ignore
@Category(SampleTest.class)
public class SplittingAndRowspan extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/splitting_and_rowspan.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SplittingAndRowspan().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(300, 150));
        doc.add(new Paragraph("Table with setSplitLate(true):")); // TODO change the text after revision
        Table table = new Table(2);
        table.setKeepTogether(true);
        table.setMarginTop(10);
        Cell cell = new Cell(3, 1);
        cell.add("G");
        cell.add("R");
        cell.add("P");
        table.addCell(cell);
        table.addCell("row 1");
        table.addCell("row 2");
        table.addCell("row 3");
        doc.add(table);
        doc.add(new Paragraph("Table with setSplitLate(false):"));
        // TODO Add clone functionality for Table
        table = new Table(2);
        table.setKeepTogether(false);
        table.setMarginTop(10);
        cell = new Cell(3, 1);
        cell.add("G");
        cell.add("R");
        cell.add("P");
        table.addCell(cell);
        table.addCell("row 1");
        table.addCell("row 2");
        table.addCell("row 3");
        doc.add(table);
        doc.close();
    }
}