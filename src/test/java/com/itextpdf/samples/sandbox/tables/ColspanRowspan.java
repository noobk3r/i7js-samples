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
public class ColspanRowspan extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/colspan_rowspan.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColspanRowspan().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(4);
        Cell cell = new Cell().add(new Paragraph(" 1,1 "));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(" 1,2 "));
        table.addCell(cell);
        Cell cell23 = new Cell(2, 2).add(new Paragraph("multi 1,3 and 1,4"));
        table.addCell(cell23);
        cell = new Cell().add(new Paragraph(" 2,1 "));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(" 2,2 "));
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
