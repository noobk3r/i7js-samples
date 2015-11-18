package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_04_10_TableHeight extends GenericTest {
    static public final String DEST = "./target/test/resources/book/part1/chapter04/Listing_04_10_TableHeight.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_10_TableHeight().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // TODO The height of table and rows is not set until adding to document
        Table table = createFirstTable();
        doc.add(new Paragraph(
                String.format("Table height before doc.add(): %f",
                        table.getHeight())));
        doc.add(new Paragraph(
                String.format("Height of the first row: %f",
                        table.getCell(0, 0).getHeight())));
        doc.add(table);
        doc.add(new Paragraph(
                String.format("Table height after doc.add(): %f",
                        table.getHeight())));
        doc.add(new Paragraph(
                String.format("Height of the first row: %f",
                        table.getCell(0, 0).getHeight())));
        table = createFirstTable();
        doc.add(new Paragraph(
                String.format("Table height before setTotalWidth(): %f",
                        table.getHeight())));
        doc.add(
                new Paragraph(String.format("Height of the first row: %f",
                        table.getCell(0, 0).getHeight())));
        table.setWidth(50);
        // TODO No setLockedWidth(boolean)
        // table.setLockedWidth(true);
        doc.add(
                new Paragraph(String.format("Table height after setTotalWidth(): %f",
                        table.getHeight())));
        doc.add(new Paragraph(String.format("Height of the first row: %f",
                table.getCell(0, 0).getHeight())));
        doc.add(table);

        doc.close();
    }

    public static Table createFirstTable() {
        // a table with three columns
        Table table = new Table(3);
        // the cell object
        Cell cell;
        // we add a cell with colspan 3
        cell = new Cell(1, 3).add(new Paragraph("Cell with colspan 3"));
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new Cell(2, 1).add(new Paragraph("Cell with rowspan 2"));
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell(new Cell().add(new Paragraph("row 1; cell 1")));
        table.addCell(new Cell().add(new Paragraph("row 1; cell 2")));
        table.addCell(new Cell().add(new Paragraph("row 2; cell 1")));
        table.addCell(new Cell().add(new Paragraph("row 2; cell 2")));
        return table;
    }
}
