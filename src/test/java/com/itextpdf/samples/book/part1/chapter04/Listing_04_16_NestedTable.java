package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_16_NestedTable extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_16_NestedTable.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_16_NestedTable().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Table table = new Table(4);
        Table nested1 = new Table(2);
        nested1.addCell("1.1");
        nested1.addCell("1.2");
        Table nested2 = new Table(1);
        nested2.addCell("12.1");
        nested2.addCell("12.2");
        for (int k = 0; k < 16; ++k) {
            if (k == 1) {
                table.addCell(nested1);
            } else if (k == 12) {
                table.addCell(nested2);
            } else {
                table.addCell("cell " + k);
            }
        }
        doc.add(table);
        doc.close();
    }
}

