package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
public class ColoredBackground extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/colored_background.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColoredBackground().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table;
        Cell cell;
        // TODO Implement usage of fontstyle Font.BOLD (itext5)
        PdfFont font = new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI));
        table = new Table(16);
        for (int aw = 0; aw < 16; aw++) {
            cell = new Cell().add(new Paragraph("hi").setFont(font).setFontSize(12).setFontColor(Color.WHITE));
            cell.setBackgroundColor(Color.BLUE);
            // TODO Is there any way to do it smartly?
            cell.setBorder(null);
            cell.setVerticalAlignment(Property.VerticalAlignment.MIDDLE);
            cell.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
            table.addCell(cell);
        }
        doc.add(table);

        doc.close();
    }
}
