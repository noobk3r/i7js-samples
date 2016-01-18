/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/29237980/itext-pdf-not-displaying-chinese-characters-when-using-noto-fonts
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
public class NotoExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/chinese.pdf";
    public static final String FONT = "./src/test/resources/sandbox/fonts/NotoSansCJKsc-Regular.otf";

    public static final String TEXT = "These are the protagonists in 'Hero', a movie by Zhang Yimou:\n"
            + "\u7121\u540d (Nameless), \u6b98\u528d (Broken Sword), "
            + "\u98db\u96ea (Flying Snow), \u5982\u6708 (Moon), "
            + "\u79e6\u738b (the King), and \u9577\u7a7a (Sky).";
    public static final String CHINESE = "\u5341\u950a\u57cb\u4f0f";
    public static final String JAPANESE = "\u8ab0\u3082\u77e5\u3089\u306a\u3044";
    public static final String KOREAN = "\ube48\uc9d1";


    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NotoExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFont.createFont(FONT, "Identity-H", true);
        Paragraph p = new Paragraph(TEXT).setFont(font);
        doc.add(p);
        doc.add(new Paragraph(CHINESE).setFont(font));
        doc.add(new Paragraph(JAPANESE).setFont(font));
        doc.add(new Paragraph(KOREAN).setFont(font));
        doc.close();
    }
}
