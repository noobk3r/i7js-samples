package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
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
public class SimpleTable5 extends GenericTest {

    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table5.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable5().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Table table = new Table(5);
        // TODO
        //table.setWidthPercentage(100);
        Cell cell = new Cell(1, 5).add(new Paragraph("Table XYZ (Continued)"));
        table.addHeaderCell(cell);
        cell = new Cell(1, 5).add(new Paragraph("Continue on next page"));
        table.addFooterCell(cell);
        table.setSkipFirstHeader(true);
        table.setSkipLastFooter(true);
        for (int i = 0; i < 350; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i+1))));
        }

        doc.add(table);

        doc.close();
    }
}
