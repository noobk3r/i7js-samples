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

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_07_Spacing extends GenericTest {
    static public final String DEST = "./target/test/resources/book/part1/chapter04/Listing_04_07_Spacing.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_07_Spacing().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);
        table.setWidthPercent(100);
        Paragraph p = new Paragraph(
                "Dr. iText or: How I Learned to Stop Worrying " +
                        "and Love the Portable Document Format.");
        Cell cell = new Cell().add(p);
        table.addCell(new Cell().add(new Paragraph("default leading / spacing")));
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("absolute leading: 20")));
        // TODO No setLeading
        // cell.setLeading(20f, 0f);
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("absolute leading: 3; relative leading: 1.2")));
        // cell.setLeading(3f, 1.2f);
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("absolute leading: 0; relative leading: 1.2")));
        // cell.setLeading(0f, 1.2f);
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("no leading at all")));
        // cell.setLeading(0f, 0f);
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(
                "Dr. iText or: How I Learned to Stop Worrying and Love PDF"));
        table.addCell(new Cell().add(new Paragraph("padding 10")));
        cell.setPadding(10);
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("padding 0")));
        cell.setPadding(0);
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("different padding for left, right, top and bottom")));
        cell.setPaddingLeft(20);
        cell.setPaddingRight(50);
        cell.setPaddingTop(0);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        p = new Paragraph("iText in Action Second Edition");
        // TODO Implement facility to set default-cell configs
        // TODO no setUseAscender(boolean) and setUseDescender(boolean)
        // table.getDefaultCell().setUseAscender(false);
        // table.getDefaultCell().setUseDescender(false);
        table.addCell(new Cell().add(new Paragraph("padding 2; no ascender, no descender")).setPadding(2));
        table.addCell(new Cell().add(p).setPadding(2));
        // table.getDefaultCell().setUseAscender(true);
        // table.getDefaultCell().setUseDescender(false);
        table.addCell(new Cell().add(new Paragraph("padding 2; ascender, no descender")).setPadding(2));
        table.addCell(new Cell().add(p).setPadding(2));
        // table.getDefaultCell().setUseAscender(false);
        // table.getDefaultCell().setUseDescender(true);
        table.addCell(new Cell().add(new Paragraph("padding 2; descender, no ascender")).setPadding(2));
        table.addCell(new Cell().add(p).setPadding(2));
        // table.getDefaultCell().setUseAscender(true);
        // table.getDefaultCell().setUseDescender(true);
        table.addCell(new Cell().add(new Paragraph("padding 2; ascender and descender")).setPadding(2));
        cell.setPadding(2);
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        table.addCell(new Cell().add(p).setPadding(2));
        doc.add(table);

        doc.close();
    }
}
