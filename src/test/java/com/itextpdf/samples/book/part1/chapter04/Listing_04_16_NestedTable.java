package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.geom.PageSize;
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

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_16_NestedTable extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_16_NestedTable.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_16_NestedTable().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());
        Table table = new Table(4);
        Table nested1 = new Table(2);
        nested1.addCell(new Cell().add(new Paragraph("1.1")));
        nested1.addCell(new Cell().add(new Paragraph("1.2")));
        Table nested2 = new Table(1);
        nested2.addCell(new Cell().add(new Paragraph("12.1")));
        nested2.addCell(new Cell().add(new Paragraph("12.2")));
        for (int k = 0; k < 16; ++k) {
            if (k == 1) {
                table.addCell(new Cell().add(nested1));
            } else if (k == 12) {
                table.addCell(new Cell().add(nested2));
            } else {
                table.addCell(new Cell().add(new Paragraph("cell " + k)));
            }
        }
        doc.add(table);
        doc.close();
    }
}

