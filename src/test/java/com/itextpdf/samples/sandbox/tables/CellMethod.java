package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class CellMethod extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/cell_method.pdf";
    public static final String FONT = "./src/test/resources/sandbox/tables/FreeSans.ttf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CellMethod().manipulatePdf(DEST);
    }

    public static Cell getNormalCell(String string, String language, float size, PdfDocument pdfDoc) throws IOException {
        if (string != null && "".equals(string)) {
            return new Cell();
        }
        PdfFont f = getFontForThisLanguage(language, pdfDoc);
        Cell cell = new Cell().add(new Paragraph(string).setFont(f));
        cell.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        if (size < 0) {
            size = -size;
            cell.setFontSize(size);
            cell.setFontColor(Color.RED);
        }
        return cell;
    }

    public static PdfFont getFontForThisLanguage(String language, PdfDocument pdfDoc) throws IOException {
        if ("czech".equals(language)) {
            return PdfFont.createFont(FONT, "Cp1250", true);
        }
        if ("greek".equals(language)) {
            return PdfFont.createFont(FONT, "Cp1253", true);
        }
        return PdfFont.createFont(FONT, null, true);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        setCompareRenders(true);
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);
        table.addCell(new Cell().add(new Paragraph("Winansi")));
        table.addCell(getNormalCell("Test", null, 12, pdfDoc));
        table.addCell(new Cell().add(new Paragraph("Winansi")));
        table.addCell(getNormalCell("Test", null, -12, pdfDoc));
        table.addCell(new Cell().add(new Paragraph("Greek")));
        table.addCell(getNormalCell("\u039d\u03cd\u03c6\u03b5\u03c2", "greek", 12, pdfDoc));
        table.addCell(new Cell().add(new Paragraph("Czech")));
        table.addCell(getNormalCell("\u010c,\u0106,\u0160,\u017d,\u0110", "czech", 12, pdfDoc));
        table.addCell(new Cell().add(new Paragraph("Test")));
        table.addCell(getNormalCell(" ", null, 12, pdfDoc));
        table.addCell(new Cell().add(new Paragraph("Test")));
        table.addCell(getNormalCell(" ", "greek", 12, pdfDoc));
        table.addCell(new Cell().add(new Paragraph("Test")));
        table.addCell(getNormalCell(" ", "czech", 12, pdfDoc));
        doc.add(table);

        doc.close();
    }
}
