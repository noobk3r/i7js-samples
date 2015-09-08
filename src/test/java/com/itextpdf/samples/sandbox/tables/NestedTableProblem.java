package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class NestedTableProblem extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/nested_table_problem.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NestedTableProblem().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(612, 792).setMargins(21, 21, 30, 35));

        // table 2
        final Table table2 = new Table(1);
        table2.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        table2.addCell(new Cell().setBorder(new SolidBorder(Color.RED, 1)).add(new Paragraph("Goodbye World")));
        table2.setWidthPercent(80);
        // table 1
        final Table table1 = new Table(1);
        table1.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        Cell cell = new Cell();
        cell.setBorder(new SolidBorder(Color.BLACK, 1));
        cell.add(new Paragraph("Hello World"));
        cell.add(table2);
        cell.add(new Paragraph("Hello World"));
        table1.addCell(cell);
        doc.add(table1);

        doc.close();
    }
}
