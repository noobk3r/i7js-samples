/**
 * Example written by Bruno Lowagie.
 */
package com.itextpdf.samples.sandbox.fonts;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class CzechExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/czech_example.pdf";
    public static final String FONT = "./src/test/resources/sandbox/fonts/FreeSans.ttf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CzechExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfFont f1 = PdfFont.createFont(pdfDoc, FONT, "Cp1250", true);
        Paragraph p1 = new Paragraph("Testing of letters \u010c,\u0106,\u0160,\u017d,\u0110").setFont(f1);
        doc.add(p1);
        PdfFont f2 = PdfFont.createFont(pdfDoc, FONT, "Identity-H", true);
        Paragraph p2 = new Paragraph("Testing of letters \u010c,\u0106,\u0160,\u017d,\u0110").setFont(f2);
        doc.add(p2);
        pdfDoc.close();
    }
}
