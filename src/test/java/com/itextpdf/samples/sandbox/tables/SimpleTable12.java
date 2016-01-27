/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
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
public class SimpleTable12 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table12.pdf";

    protected PdfFont font;

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable12().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        font = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);
        Table table = new Table(8);
        table.addCell(createCell("Examination", 1, 2, 15));
        table.addCell(createCell("Board", 1, 2, 15));
        table.addCell(createCell("Month and Year of Passing", 1, 2, 15));
        table.addCell(createCell("", 1, 1, 1));
        table.addCell(createCell("Marks", 2, 1, 1));
        table.addCell(createCell("Percentage", 1, 2, 15));
        table.addCell(createCell("Class / Grade", 1, 2, 15));
        table.addCell(createCell("", 1, 1, 15));
        table.addCell(createCell("Obtained", 1, 1, 15));
        table.addCell(createCell("Out of", 1, 1, 15));
        table.addCell(createCell("12th / I.B. Diploma", 1, 1, 15));
        table.addCell(createCell("", 1, 1, 15));
        table.addCell(createCell("", 1, 1, 15));
        table.addCell(createCell("Aggregate (all subjects)", 1, 1, 15));
        table.addCell(createCell("", 1, 1, 15));
        table.addCell(createCell("", 1, 1, 15));
        table.addCell(createCell("", 1, 1, 15));
        table.addCell(createCell("", 1, 1, 15));
        doc.add(table);

        doc.close();
    }

    public Cell createCell(String content, int colspan, int rowspan, int border) {
        Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(10));
        cell.setBorder(null);
        if (8 == (border & 8)) {
            cell.setBorderRight(new SolidBorder(1));
            cell.setBorderBottom(new SolidBorder(1));
        }
        if (4 == (border & 4)) {
            cell.setBorderLeft(new SolidBorder(1));
        }
        if (2 == (border & 2)) {
            cell.setBorderBottom(new SolidBorder(1));
        }
        if (1 == (border & 1)) {
            cell.setBorderTop(new SolidBorder(1));
        }
        cell.setTextAlignment(Property.TextAlignment.CENTER);
        return cell;
    }
}
