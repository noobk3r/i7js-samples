package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SimpleTable10 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table10.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable10().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(5);
        Cell sn = new Cell(2, 1).add(new Paragraph("S/N"));
        sn.setBackgroundColor(Color.YELLOW);
        table.addCell(sn);
        Cell name = new Cell(1, 3).add(new Paragraph("Name"));
        name.setBackgroundColor(Color.CYAN);
        table.addCell(name);
        Cell age = new Cell(2, 1).add(new Paragraph("Age"));
        age.setBackgroundColor(Color.GRAY);
        table.addCell(age);
        Cell surname = new Cell().add(new Paragraph("SURNAME"));
        surname.setBackgroundColor(Color.BLUE);
        table.addCell(surname);
        Cell firstname = new Cell().add(new Paragraph("FIRST NAME"));
        firstname.setBackgroundColor(Color.RED);
        table.addCell(firstname);
        Cell middlename = new Cell().add(new Paragraph("MIDDLE NAME"));
        middlename.setBackgroundColor(Color.GREEN);
        table.addCell(middlename);
        Cell f1 = new Cell().add(new Paragraph("1"));
        f1.setBackgroundColor(Color.PINK);
        table.addCell(f1);
        Cell f2 = new Cell().add(new Paragraph("James"));
        f2.setBackgroundColor(Color.MAGENTA);
        table.addCell(f2);
        Cell f3 = new Cell().add(new Paragraph("Fish"));
        f3.setBackgroundColor(Color.ORANGE);
        table.addCell(f3);
        Cell f4 = new Cell().add(new Paragraph("Stone"));
        f4.setBackgroundColor(Color.DARK_GRAY);
        table.addCell(f4);
        Cell f5 = new Cell().add(new Paragraph("17"));
        f5.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(f5);
        doc.add(table);

        doc.close();
    }
}
