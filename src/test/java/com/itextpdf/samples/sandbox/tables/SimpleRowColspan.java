package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class SimpleRowColspan extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_row_colspan.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleRowColspan().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{1, 2, 2, 2, 1});
        table.setWidth(0);
        Cell cell;
        cell = new Cell(2, 1).add(new Paragraph("S/N"));
        table.addCell(cell);
        cell = new Cell(1, 3).add(new Paragraph("Name"));
        table.addCell(cell);
        cell = new Cell(2, 1).add(new Paragraph("Age"));
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("SURNAME")));
        table.addCell(new Cell().add(new Paragraph("FIRST NAME")));
        table.addCell(new Cell().add(new Paragraph("MIDDLE NAME")));
        table.addCell(new Cell().add(new Paragraph("1")));
        table.addCell(new Cell().add(new Paragraph("James")));
        table.addCell(new Cell().add(new Paragraph("Fish")));
        table.addCell(new Cell().add(new Paragraph("Stone")));
        table.addCell(new Cell().add(new Paragraph("17")));
        doc.add(table);

        doc.close();
    }
}
