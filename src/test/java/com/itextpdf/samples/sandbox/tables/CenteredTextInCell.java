/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class CenteredTextInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/centered_text_in_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CenteredTextInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFontFactory.createFont(FontFactory.createFont(FontConstants.HELVETICA_BOLD));
        Paragraph para = new Paragraph("Test").setFont(font).setFontSize(12);
        para.setFixedLeading(0);
        para.setMultipliedLeading(1);
        Table table = new Table(1);
        Cell cell = new Cell();
        cell.setHeight(50);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add(para);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
