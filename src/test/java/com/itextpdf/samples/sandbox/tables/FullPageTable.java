/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class FullPageTable extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/full_page_table.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FullPageTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(595, 842));
        doc.setMargins(0, 0, 0, 0);

        Table table = new Table(10);
        table.setMarginTop(0f);
        table.setMarginBottom(0f);
        // first row
        Cell cell = new Cell(1, 10).add(new Paragraph("DateRange"));
        cell.setTextAlignment(Property.TextAlignment.CENTER);
        cell.setPadding(5.0f);
        cell.setBackgroundColor(new DeviceRgb(140, 221, 8));
        table.addCell(cell);

        table.addCell("Calldate");
        table.addCell("Calltime");
        table.addCell("Source");
        table.addCell("DialedNo");
        table.addCell("Extension");
        table.addCell("Trunk");
        table.addCell("Duration");
        table.addCell("Calltype");
        table.addCell("Callcost");
        table.addCell("Site");

        for (int i = 0; i < 100; i++) {
            table.addCell("date" + i);
            table.addCell("time" + i);
            table.addCell("source" + i);
            table.addCell("destination" + i);
            table.addCell("extension" + i);
            table.addCell("trunk" + i);
            table.addCell("dur" + i);
            table.addCell("toc" + i);
            table.addCell("callcost" + i);
            table.addCell("Site" + i);
        }
        doc.add(table);

        doc.close();
    }
}
