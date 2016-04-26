/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.property.TabAlignment;
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
public class DottedLineLeader extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/dotted_line_leader.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DottedLineLeader().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{1, 3, 1});
        table.setWidthPercent(50);
        ILineDrawer leader = new DottedLine(1.5f, 6);

        Paragraph p;
        table.addCell(getCell(new Paragraph("fig 1"), VerticalAlignment.TOP));
        p = new Paragraph("Title text");

        p.addTabStops(new TabStop(150, TabAlignment.RIGHT, leader));
        p.add(new Tab());

        table.addCell(getCell(p, VerticalAlignment.TOP));
        table.addCell(getCell(new Paragraph("2"), VerticalAlignment.BOTTOM));
        table.addCell(getCell(new Paragraph("fig 2"), VerticalAlignment.TOP));
        p = new Paragraph("This is a longer title text that wraps");

        p.addTabStops(new TabStop(150, TabAlignment.RIGHT, leader));
        p.add(new Tab());

        table.addCell(getCell(p, VerticalAlignment.TOP));
        table.addCell(getCell(new Paragraph("55"), VerticalAlignment.BOTTOM));
        table.addCell(getCell(new Paragraph("fig 3"), VerticalAlignment.TOP));
        p = new Paragraph("Another title text");


        table.addCell(getCell(p, VerticalAlignment.TOP));
        p.addTabStops(new TabStop(150, TabAlignment.RIGHT, leader));
        p.add(new Tab());
        table.addCell(getCell(new Paragraph("89"), VerticalAlignment.BOTTOM));


        doc.add(table);

        doc.close();
    }

    public Cell getCell(Paragraph p, VerticalAlignment verticalAlignment) {
        Cell cell = new Cell();
        cell.setVerticalAlignment(verticalAlignment);
        p.setMargin(2);
        p.setMultipliedLeading(1);
        cell.add(p);
        return cell;
    }
}
