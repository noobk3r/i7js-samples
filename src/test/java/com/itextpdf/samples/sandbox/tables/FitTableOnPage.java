/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutContext;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FitTableOnPage extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/fit_table_on_page.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FitTableOnPage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Table table = new Table(1);
        table.setWidth(550);
        for (int i = 0; i < 10; i++) {
            Cell cell;
            if (i == 9) {
                cell = new Cell().add(new Paragraph("Two\nLines"));
            } else {
                cell = new Cell().add(new Paragraph(Integer.toString(i)));
            }
            table.addCell(cell);
        }
        LayoutResult tableLayoutResult = table.createRendererSubTree().layout(new LayoutContext(new LayoutArea(1, new Rectangle(600, 10000))));

        PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(612, tableLayoutResult.getOccupiedArea().getBBox().getHeight() + 72));
        doc.add(table);
        doc.close();
    }
}
