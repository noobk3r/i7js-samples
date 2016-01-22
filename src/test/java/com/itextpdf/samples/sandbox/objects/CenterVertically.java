/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26752663/adding-maps-at-itext-java
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
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
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class CenterVertically extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/center_vertically.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CenterVertically().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        Table table;
        Cell cell = new Cell();
        for (int i = 1; i <= 5; i++)
            cell.add(new Paragraph("Line " + i));
        table = new Table(1);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        addTable(pdfDoc, table);
        pdfDoc.addNewPage();
        table = new Table(1);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        addTable(pdfDoc, table);

        doc.close();
    }

    public void addTable(PdfDocument pdfDoc, Table table) {
        Rectangle pagedimension = new Rectangle(36, 36, 559, 806);
        Rectangle rect = pagedimension;
        drawColumnText(pdfDoc, rect, table);
    }

    public void drawColumnText(PdfDocument pdfDoc, Rectangle rect, Table table) {
        // TODO Since we don't know paragraphs' positions before rendering, we can't center text (feels the absence of SimpleColumn)
        new Document(pdfDoc).add(table
                .setHeight(rect.getHeight())
                .setWidth(rect.getWidth())
                .setMarginLeft(rect.getLeft()));
    }
}
