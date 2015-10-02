package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FitTableOnPage extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/fit_table_on_page.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FitTableOnPage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Table table = new Table(1);
        table.setWidth(550);
        for (int i = 0; i < 10; i++) {
            Cell cell;
            if (i == 9) {
                cell = new Cell().add(new Paragraph("Two\nLines"));
            } else {
                cell = new Cell().add(new Paragraph(Integer.toString(i)));
            }
            table.addCell(cell);
        }

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        // TODO getHeight() returns NullPointerException. Need smth like preFormat()
        //Document doc = new Document(pdfDoc, new PageSize(612, table.getHeight() + 72));
        //doc.add(table);
        //doc.close();
    }
}
