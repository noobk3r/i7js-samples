package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
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
public class CenteredTextInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/centered_text_in_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CenteredTextInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        // TODO Implement fontstyles (Font.BOLD in itext5)
        PdfFont font = new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI));
        Paragraph para = new Paragraph("Test").setFont(font).setFontSize(12);
        para.setFixedLeading(0);
        para.setMultipliedLeading(1);
        Table table = new Table(1);
        // TODO Implement setWidthPercentage(float)
        // table.setWidthPercentage(100);
        Cell cell = new Cell();
        cell.setHeight(50);
        cell.setVerticalAlignment(Property.VerticalAlignment.MIDDLE);
        cell.add(para);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
