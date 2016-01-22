package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_05_14_NewPage extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter05/Listing_05_14_NewPage.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_14_NewPage().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());
        doc.add(new Paragraph("This page will NOT be followed by a blank page!"));
        doc.add(new AreaBreak());
        // TODO The system of new paging is a bit different from itext5, so this example has a bit different sense
        // we don't add anything to this page: newPage() will be ignored
        pdfDoc.addNewPage();
        doc.add(new Paragraph("This page will be followed by a blank page!"));
        doc.add(new AreaBreak());
        // TODO There is no setPageEmpty(boolean)
//        writer.setPageEmpty(false);
        doc.add(new AreaBreak());
        doc.add(new Paragraph("The previous page was a blank page!"));
        doc.close();
    }
}


