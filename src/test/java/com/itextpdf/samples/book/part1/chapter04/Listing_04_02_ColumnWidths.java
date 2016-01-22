package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_02_ColumnWidths extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter04/Listing_04_02_ColumnWidths.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_02_ColumnWidths().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = createTable1();
        doc.add(table);
        table = createTable2();
        table.setMarginTop(5);
        table.setMarginBottom(5);
        doc.add(table);
        table = createTable3();
        doc.add(table);
        table = createTable4();
        table.setMarginTop(5);
        table.setMarginBottom(5);
        doc.add(table);
        table = createTable5();
        doc.add(table);

        doc.close();
    }

    public static Table createTable1() {
        Table table = new Table(new float[]{2, 1, 1});
        table.setWidthPercent(288 / 5.23f);
        Cell cell;
        cell = new Cell(1, 3).add("Table 1");
        table.addCell(cell);
        cell = new Cell(2, 1).add("Cell with rowspan 2");
        table.addCell(cell);
        table.addCell(new Cell().add("row 1; cell 1"));
        table.addCell(new Cell().add("row 1; cell 2"));
        table.addCell(new Cell().add("row 2; cell 1"));
        table.addCell(new Cell().add("row 2; cell 2"));
        return table;
    }

    public static Table createTable2() {
        Table table = new Table(new float[]{2, 1, 1});
        table.setWidth(288);
        // TODO No setLockedWidth(boolean)
        // table.setLockedWidth(true);
        Cell cell;
        cell = new Cell(1, 3).add("Table 2");
        table.addCell(cell);
        cell = new Cell(2, 1).add("Cell with rowspan 2");
        table.addCell(cell);
        table.addCell(new Cell().add("row 1; cell 1"));
        table.addCell(new Cell().add("row 1; cell 2"));
        table.addCell(new Cell().add("row 2; cell 1"));
        table.addCell(new Cell().add("row 2; cell 2"));
        return table;
    }

    public static Table createTable3() {
        Table table = new Table(new float[]{2, 1, 1});
        table.setWidthPercent(55.067f);
        Cell cell;
        cell = new Cell(1, 3).add("Table 3");
        table.addCell(cell);
        cell = new Cell(2, 1).add("Cell with rowspan 2");
        table.addCell(cell);
        table.addCell(new Cell().add("row 1; cell 1"));
        table.addCell(new Cell().add("row 1; cell 2"));
        table.addCell(new Cell().add("row 2; cell 1"));
        table.addCell(new Cell().add("row 2; cell 2"));
        return table;
    }

    public static Table createTable4() {
        Table table = new Table(new float[]{144, 72, 72});
        Rectangle rect = new Rectangle(523, 770);
        // The next line is needed in itext5, although it will cause no difference in itext6
        // table.setWidthPercent((144 + 72 + 72) / 523f * 100);
        Cell cell;
        cell = new Cell(1, 3).add("Table 4");
        table.addCell(cell);
        cell = new Cell(2, 1).add("Cell with rowspan 2");
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    public static Table createTable5() {
        Table table = new Table(new float[]{144, 72, 72});
        Cell cell;
        cell = new Cell(1, 3).add("Table 5");
        table.addCell(cell);
        cell = new Cell(2, 1).add("Cell with rowspan 2");
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
}
