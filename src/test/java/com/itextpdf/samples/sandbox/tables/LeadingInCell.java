package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class LeadingInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/leading_in_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new LeadingInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        Cell cell = new Cell();
        Paragraph p;
        p = new Paragraph("paragraph 1: leading 16").setFixedLeading(16);
        cell.add(p);
        p = new Paragraph("paragraph 2: leading 32").setFixedLeading(32);
        cell.add(p);
        p = new Paragraph("paragraph 3: leading 10").setFixedLeading(10);
        cell.add(p);
        p = new Paragraph("paragraph 4: leading 18").setFixedLeading(18);
        cell.add(p);
        p = new Paragraph("paragraph 5: leading 40").setFixedLeading(40);
        cell.add(p);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
