package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
public class SimpleTable8 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table8.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable8().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(3);
        // TODO Implement setWidthPercentage(float) method
        // table.setWidthPercentage(100);
        //PdfReader reader = new PdfReader("./target/test/resources/sandbox/tables/header.pdf");
        // TODO Implement functionality to do things below
        // PdfImportedPage header = writer.getImportedPage(reader, 1);
        // Cell cell = new Cell(1,3).add(Image.getInstance(header));
        //table.addCell(cell);
        for (int row = 1; row <= 50; row++) {
            for (int column = 1; column <= 3; column++) {
                table.addCell(new Cell().add(new Paragraph(String.format("row %s, column %s", row, column))));
            }
        }
        //reader = new PdfReader("./target/test/resources/sandbox/tables/footer.pdf");
        // TODO Implement functionality to do things below
        //PdfImportedPage footer = writer.getImportedPage(reader, 1);
        //cell = new Cell(1,3).add(Image.getInstance(footer));
        //table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
