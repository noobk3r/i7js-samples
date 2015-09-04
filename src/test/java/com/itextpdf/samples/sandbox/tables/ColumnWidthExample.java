package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.color.DeviceGray;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.basics.geom.PageSize;
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
public class ColumnWidthExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/column_width_example.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColumnWidthExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        float[] columnWidths = {1, 5, 5};
        Table table = new Table(columnWidths);
        // TODO
        //table.setWidthPercentage(100);
        table.setWidth(770);
        //table.getDefaultCell().setUseAscender(true);
        //table.getDefaultCell().setUseDescender(true);
        PdfFont f = new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""));
        Cell cell = new Cell(1, 3).add(new Paragraph("This is a header")).
                setFont(f).
                setFontSize(13).
                setFontColor(DeviceGray.WHITE).
                setBackgroundColor(DeviceGray.BLACK).
                setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        table.addHeaderCell(cell);
        for (int i = 0; i < 2; i++) {
            Cell[] headerFooter = new Cell[] {
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("#")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Key")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Value"))
            };
            for (Cell hfCell : headerFooter) {
                if (i == 0) {
                    table.addHeaderCell(hfCell);
                } else {
                    table.addFooterCell(hfCell);
                }
            }
        }
        for (int counter = 1; counter < 101; counter++) {
            table.addCell(new Cell().setHorizontalAlignment(Property.HorizontalAlignment.CENTER).add(new Paragraph(String.valueOf(counter))));
            table.addCell(new Cell().setHorizontalAlignment(Property.HorizontalAlignment.CENTER).add(new Paragraph("key " + counter)));
            table.addCell(new Cell().setHorizontalAlignment(Property.HorizontalAlignment.CENTER).add(new Paragraph("value " + counter)));
        }
        doc.add(table);
        doc.close();
    }
}
