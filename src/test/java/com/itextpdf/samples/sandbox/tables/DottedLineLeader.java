package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
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
public class DottedLineLeader extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/dotted_line_leader.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DottedLineLeader().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{1, 3, 1});
        table.setWidthPercent(50);
        // TODO Implement LineSeparator and all extended classes
        // Chunk leader = new Chunk(new DottedLineSeparator());
        Text leader = new Text("");
        Paragraph p;
        table.addCell(getCell(new Paragraph("fig 1"), Property.VerticalAlignment.TOP));
        p = new Paragraph("Title text");
        p.add(leader);
        table.addCell(getCell(p, Property.VerticalAlignment.TOP));
        table.addCell(getCell(new Paragraph("2"), Property.VerticalAlignment.BOTTOM));
        table.addCell(getCell(new Paragraph("fig 2"), Property.VerticalAlignment.TOP));
        p = new Paragraph("This is a longer title text that wraps");
        p.add(leader);
        table.addCell(getCell(p, Property.VerticalAlignment.TOP));
        table.addCell(getCell(new Paragraph("55"), Property.VerticalAlignment.BOTTOM));
        table.addCell(getCell(new Paragraph("fig 3"), Property.VerticalAlignment.TOP));
        p = new Paragraph("Another title text");
        p.add(leader);
        table.addCell(getCell(p, Property.VerticalAlignment.TOP));
        table.addCell(getCell(new Paragraph("89"), Property.VerticalAlignment.BOTTOM));
        doc.add(table);

        doc.close();
    }

    public Cell getCell(Paragraph p, Property.VerticalAlignment verticalAlignment) {
        Cell cell = new Cell();
        cell.setVerticalAlignment(verticalAlignment);
        // TODO Implement setUseAscender/Descender(boolean)
        //cell.setUseAscender(true);
        //cell.setUseDescender(true);
        cell.add(p);
        return cell;
    }
}
