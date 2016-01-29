/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SimpleTable8 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table8.pdf";
    public static final String SRC = "./src/test/resources/sandbox/tables/";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable8().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(3);
        PdfReader reader = new PdfReader(SRC + "header.pdf");
        PdfDocument srcDoc = new PdfDocument(reader);
        PdfFormXObject header = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);
        Cell cell = new Cell(1,3).add(new Image(header).setAutoScale(true));
        table.addCell(cell);
        for (int row = 1; row <= 50; row++) {
            for (int column = 1; column <= 3; column++) {
                table.addCell(String.format("row %s, column %s", row, column));
            }
        }
        reader = new PdfReader(SRC + "footer.pdf");
        srcDoc = new PdfDocument(reader);
        PdfFormXObject footer = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);
        cell = new Cell(1,3).add(new Image(footer).setAutoScale(true));
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
