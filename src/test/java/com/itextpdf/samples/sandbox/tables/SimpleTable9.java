package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

public class SimpleTable9 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table9.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable9().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("With 3 columns:"));
        Table table = new Table(new float[]{1, 1, 8});
        table.setWidth(0);
        table.setMarginTop(5);
        // TODO Implement setWidthPercentage(float) method
        // table.setWidthPercentage(100);
        table.addCell(new Cell().add(new Paragraph("Col a")));
        table.addCell(new Cell().add(new Paragraph("Col b")));
        table.addCell(new Cell().add(new Paragraph("Col c")));
        table.addCell(new Cell().add(new Paragraph("Value a")));
        table.addCell(new Cell().add(new Paragraph("Value b")));
        table.addCell(new Cell().add(new Paragraph("This is a long description for column c. " +
                "It needs much more space hence we made sure that the third column is wider.")));
        doc.add(table);
        doc.add(new Paragraph("With 2 columns:"));
        table = new Table(2);
        table.setMarginTop(5);
        // TODO Implement setWidthPercentage(float) method
        // table.setWidthPercentage(100);
        // TODO Implement setting default colsapn/rowspan characteristics for all cell in a table
        // table.getDefaultCell().setColspan(1);
        table.addCell(new Cell().add(new Paragraph("Col a")));
        table.addCell(new Cell().add(new Paragraph("Col b")));
        table.addCell(new Cell().add(new Paragraph("Value a")));
        table.addCell(new Cell().add(new Paragraph("Value b")));
        // TODO Implement setting default colsapn/rowspan characteristics for all cell in a table
        // table.getDefaultCell().setColspan(2);
        table.addCell(new Cell(1, 2).add(new Paragraph("Value b")));
        table.addCell(new Cell(1, 2).add(new Paragraph("This is a long description for column c. " +
                "It needs much more space hence we made sure that the third column is wider.")));
        // TODO Implement setting default colsapn/rowspan characteristics for all cell in a table
        //table.getDefaultCell().setColspan(1);
        table.addCell(new Cell().add(new Paragraph("Col a")));
        table.addCell(new Cell().add(new Paragraph("Col b")));
        table.addCell(new Cell().add(new Paragraph("Value a")));
        table.addCell(new Cell().add(new Paragraph("Value b")));
        //table.getDefaultCell().setColspan(2);
        table.addCell(new Cell(1, 2).add(new Paragraph("Value b")));
        table.addCell(new Cell(1, 2).add(new Paragraph("This is a long description for column c. " +
                "It needs much more space hence we made sure that the third column is wider.")));
        doc.add(table);

        doc.close();
    }
}
