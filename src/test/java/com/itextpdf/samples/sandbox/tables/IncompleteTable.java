package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class IncompleteTable extends GenericTest {

    public static final String DEST = "./target/test/resources/sandbox/tables/incomplete_table.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new IncompleteTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(5, true);

        for (int i = 0; i < 5; i++) {
            table.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("Header " + i)));
        }

        doc.add(table);
        for (int i = 0; i < 500; i++) {
            if (i % 5 == 0) {
                table.flush();
            }
            table.addCell(new Cell().setKeepTogether(true).add(new Paragraph("Test " + i).setMargins(0, 0, 0, 0)));
        }

        table.complete();

        doc.close();
    }
}
