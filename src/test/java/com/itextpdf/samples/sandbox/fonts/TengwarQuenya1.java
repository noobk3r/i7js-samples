/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27842872/finding-trouble-to-change-installed-font-using-itext-library
 */
package com.itextpdf.samples.sandbox.fonts;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TengwarQuenya1 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/tengwar_quenya1.pdf";
    public static final String FONT = "./src/test/resources/sandbox/fonts/QUENCAP1.TTF";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TengwarQuenya1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFont.createFont(FONT, "WINANSI", true);
        Paragraph p = new Paragraph("\"A Hello World PDF document.").setFont(font).setFontSize(12);
        doc.add(p);
        doc.close();
    }
}
