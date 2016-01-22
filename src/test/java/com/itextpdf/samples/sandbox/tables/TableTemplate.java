package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TableTemplate extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/table_template.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableTemplate().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);

        Table table = new Table(15);
        table.setWidth(1500);
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 15; c++) {
                cell = new Cell();
                cell.setHeight(49);
                cell.add(new Paragraph(String.valueOf((char) r) + String.valueOf(c)));
                table.addCell(cell);
            }
        }

        PdfFormXObject tableTemplate = new PdfFormXObject(new Rectangle(1500, 1300));
        Canvas canvas = new Canvas(tableTemplate, pdfDoc);
        canvas.add(table);
        PdfFormXObject clip;
        for (int j = 0; j < 1500; j += 500) {
            for (int i = 1300; i > 0; i -= 650) {
                clip = new PdfFormXObject(new Rectangle(500, 650));
                new PdfCanvas(clip, pdfDoc).addXObject(tableTemplate, -j, 650 - i);
                new PdfCanvas(pdfDoc.addNewPage()).addXObject(clip, 36, 156);
            }
        }

        pdfDoc.close();
    }
}
