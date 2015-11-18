package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_05_TableAlignment extends GenericTest {
    static public final String DEST = "./target/test/resources/book/part1/chapter04/Listing_04_05_TableAlignment.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_05_TableAlignment().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = createFirstTable();
        table.setWidthPercent(50);
        table.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        doc.add(table);
        table.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        doc.add(table);
        table.setHorizontalAlignment(Property.HorizontalAlignment.RIGHT);
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
