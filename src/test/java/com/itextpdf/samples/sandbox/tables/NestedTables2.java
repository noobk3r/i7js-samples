package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

public class NestedTables2 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/nested_tables2.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NestedTables2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{1, 15});
        table.setWidth(0);
        // TODO Implement setSplitLate(boolean) method
        //table.setSplitLate(false);
        for (int i = 1; i <= 20; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
            table.addCell(new Cell().add(new Paragraph("It is not smart to use iText 2.1.7!")));
        }
        Table innertable = new Table(new float[]{1, 15});
        innertable.setWidth(0);
        for (int i = 0; i < 90; i++) {
            innertable.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
            innertable.addCell(new Cell().add(new Paragraph("Upgrade if you're a professional developer!")));
        }
        table.addCell(new Cell().add(new Paragraph("21")));
        table.addCell(new Cell().add(innertable));
        for (int i = 22; i <= 40; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
            table.addCell(new Cell().add(new Paragraph("It is not smart to use iText 2.1.7!")));
        }
        doc.add(table);

        doc.close();
    }
}
