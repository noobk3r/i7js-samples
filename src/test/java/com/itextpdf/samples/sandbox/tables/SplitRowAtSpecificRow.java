package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
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
public class SplitRowAtSpecificRow extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/split_row_at_specific_row.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SplitRowAtSpecificRow().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Table table = new Table(1);
        table.setWidth(550);
        // the number of iterations has been changed in order to provide the same as in itext5 example
        for (int i = 0; i < 5; i++) {
            Cell cell;
            if (i == 4) {
                cell = new Cell().add(new Paragraph("Three\nLines\nHere"));
            }
            else {
                cell = new Cell().add(new Paragraph(Integer.toString(i)));
            }
            table.addCell(cell);
        }

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(612, 242));
        // TODO Implement setSplitLate(boolean)
        // table.setSplitLate(false);
        // TODO Implement setBreakPoints(int)
        // table.setBreakPoints(8);
        doc.add(table);

        doc.close();
    }
}