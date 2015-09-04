package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.canvas.PdfCanvas;
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
        Document doc = new Document(pdfDoc);

        Table table = new Table(15);
        table.setWidth(1500);
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 15; c++) {
                cell = new Cell();
                cell.setHeight(50);
                cell.add(new Paragraph(String.valueOf((char) r) + String.valueOf(c)));
                table.addCell(cell);
            }
        }
        //PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO Implement Templates with tables
        //PdfTemplate tableTemplate = canvas.createTemplate(1500, 1300);
        // TODO Implement writeSelectedRows(...) functionality
        //table.writeSelectedRows(0, -1, 0, 1300, tableTemplate);
        // TODO Implement PdfTemplate usage ins such situations
        //PdfTemplate clip;
        for (int j = 0; j < 1500; j += 500) {
            for (int i = 1300; i > 0; i -= 650) {
                //clip = canvas.createTemplate(500, 650);
                //clip.addTemplate(tableTemplate, -j, 650 - i);
                //canvas.addTemplate(clip, 36, 156);
                //document.newPage();
            }
        }
        doc.add(table);

        doc.close();
    }
}
