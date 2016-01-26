package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SimpleTable6 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table6.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable6().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);
        table.addCell(new Cell().add(new Paragraph("0123456789")));
        PdfFont font = PdfFont.createFont(FontFactory.createFont(FontConstants.HELVETICA));
        table.addCell(new Cell().add(new Paragraph("0123456789").setFont(font).setFontSize(12).setLineThrough()));
        Text text1 = new Text("0123456789");
        text1.setUnderline(1.5f, -1);
        table.addCell(new Cell().add(new Paragraph().add(text1)));
        Text text2 = new Text("0123456789");
        text2.setUnderline(1.5f, 3.5f);
        table.addCell(new Cell().add(new Paragraph(text2)));
        doc.add(table);

        doc.close();
    }
}
