/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SimpleTable3 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table3.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable3().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A3.rotate());

        Table table = new Table(35);
        table.setWidth(pdfDoc.getDefaultPageSize().getWidth() - 80);
        Cell contractor = new Cell(1, 5).add("XXXXXXXXXXXXX");
        table.addCell(contractor);
        Cell workType = new Cell(1,5).add("Refractory Works");
        table.addCell(workType);
        Cell supervisor = new Cell(1,4).add("XXXXXXXXXXXXXX");
        table.addCell(supervisor);
        Cell paySlipHead = new Cell(1,10).add("XXXXXXXXXXXXXXXX");
        table.addCell(paySlipHead);
        Cell paySlipMonth = new Cell(1,2).add("XXXXXXX");
        table.addCell(paySlipMonth);
        Cell blank = new Cell(1,9).add("");
        table.addCell(blank);
        doc.add(table);

        doc.close();
    }
}
