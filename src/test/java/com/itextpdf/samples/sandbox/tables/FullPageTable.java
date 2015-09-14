package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.color.DeviceRgb;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class FullPageTable extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/full_page_table.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FullPageTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(595, 842).setMargins(0, 0, 0, 0));

        Table table = new Table(10);
        table.setMarginTop(0f);
        table.setMarginBottom(0f);
        // first row
        Cell cell = new Cell(1, 10).add(new Paragraph("DateRange"));
        cell.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        cell.setPadding(5.0f);
        cell.setBackgroundColor(new DeviceRgb(140, 221, 8));
        table.addCell(cell);

        table.addCell(new Cell().add(new Paragraph("Calldate")));
        table.addCell(new Cell().add(new Paragraph("Calltime")));
        table.addCell(new Cell().add(new Paragraph("Source")));
        table.addCell(new Cell().add(new Paragraph("DialedNo")));
        table.addCell(new Cell().add(new Paragraph("Extension")));
        table.addCell(new Cell().add(new Paragraph("Trunk")));
        table.addCell(new Cell().add(new Paragraph("Duration")));
        table.addCell(new Cell().add(new Paragraph("Calltype")));
        table.addCell(new Cell().add(new Paragraph("Callcost")));
        table.addCell(new Cell().add(new Paragraph("Site")));

        for (int i = 0; i < 100; i++) {
            table.addCell(new Cell().add(new Paragraph("date" + i)));
            table.addCell(new Cell().add(new Paragraph("time" + i)));
            table.addCell(new Cell().add(new Paragraph("source" + i)));
            table.addCell(new Cell().add(new Paragraph("destination" + i)));
            table.addCell(new Cell().add(new Paragraph("extension" + i)));
            table.addCell(new Cell().add(new Paragraph("trunk" + i)));
            table.addCell(new Cell().add(new Paragraph("dur" + i)));
            table.addCell(new Cell().add(new Paragraph("toc" + i)));
            table.addCell(new Cell().add(new Paragraph("callcost" + i)));
            table.addCell(new Cell().add(new Paragraph("Site" + i)));
        }
        doc.add(table);

        doc.close();
    }
}
