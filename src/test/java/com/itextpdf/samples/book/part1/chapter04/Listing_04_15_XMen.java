package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

// TODO vertical alignment does not work
@Category(SampleTest.class)
public class Listing_04_15_XMen extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_15_XMen.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_15_XMen().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        Image[] img = {
                new Image(ImageFactory.getImage(String.format(RESOURCE, "0120903"))),
                new Image(ImageFactory.getImage(String.format(RESOURCE, "0290334"))),
                new Image(ImageFactory.getImage(String.format(RESOURCE, "0376994"))),
                new Image(ImageFactory.getImage(String.format(RESOURCE, "0348150")))
        };
        // Creates a table with 6 columns
        Table table = new Table(6);
        // first movie
        // TODO Implement the facility to set default-cell properties
        table.addCell(new Cell().add(new Paragraph("X-Men"))
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.TOP));
        // we wrap he image in a PdfPCell
        Cell cell = new Cell()
                .add(img[0])
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.TOP);
        table.addCell(cell);
        // second movie
        table.addCell(new Cell()
                .add(new Paragraph("X2"))
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.MIDDLE));
        // we wrap the image in a PdfPCell and let iText scale it
        cell = new Cell()
                .add(img[1].setAutoScale(true))
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.MIDDLE);
        table.addCell(cell);
        // third movie
        // table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(new Cell()
                .add(new Paragraph("X-Men: The Last Stand"))
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.BOTTOM));
        // we add the image with addCell()
        table.addCell(new Cell()
                .add(img[2])
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.BOTTOM));
        // fourth movie
        table.addCell(new Cell().add(new Paragraph("Superman Returns")));
        cell = new Cell();
        // we add it with addElement(); it can only take 50% of the width.
        img[3].setWidthPercent(50);
        cell
                .add(img[3])
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
                .setVerticalAlignment(Property.VerticalAlignment.BOTTOM);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}

