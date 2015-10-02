package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class CellWithGlue extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/cell_with_glue.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CellWithGlue().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table;
        Cell cell;
        table = new Table(2);
        table.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        table.setWidthPercent(60);
        // TODO margins don't work
        table.setMarginBottom(20);
        cell = new Cell().add(new Paragraph("Received Rs (in Words):"));
        cell.setBorder(null);
        cell.setBorderLeft(new SolidBorder(1));
        cell.setBorderTop(new SolidBorder(1));
        cell.setBorderBottom(new SolidBorder(1));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("Priceless"));
        cell.setHorizontalAlignment(Property.HorizontalAlignment.RIGHT);
        cell.setBorder(null);
        cell.setBorderRight(new SolidBorder(1));
        cell.setBorderTop(new SolidBorder(1));
        cell.setBorderBottom(new SolidBorder(1));
        table.addCell(cell);
        doc.add(table);
        table.setWidthPercent(50);
        doc.add(table);
        table = new Table(1);
        table.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        table.setWidthPercent(50);
        Paragraph p = new Paragraph();
        p.add(new Text("Received Rs (In Words):"));
        // TODO Implement VerticalPositionMark
        // p.add(new Text(new VerticalPositionMark()));
        p.add(new Text("Priceless"));
        table.addCell(new Cell().add(p));
        doc.add(table);

        doc.close();
    }
}
