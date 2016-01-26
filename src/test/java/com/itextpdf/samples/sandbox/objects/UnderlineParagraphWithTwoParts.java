/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27591313/how-to-restrict-number-of-characters-in-a-phrase-in-pdf
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class UnderlineParagraphWithTwoParts extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/underline_paragraph_with_two_parts.pdf";
    // TODO There is no VerticalPositionMark
    // public static final Chunk GLUE = new Chunk(new VerticalPositionMark());

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new UnderlineParagraphWithTwoParts().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        pdfDoc.addNewPage();
        PdfFont font = PdfFontFactory.createFont(FontConstants.COURIER, "WinAnsi", false);
        float charWidth = font.getWidth(" ");
        int charactersPerLine = 101;
        float pageWidth = pdfDoc.getPage(1).getPageSize().getWidth();
        float fontSize = (1000 * (pageWidth / (charWidth * charactersPerLine)));
        fontSize = ((int) (fontSize * 100)) / 100f;
        String string2 = "0123456789";
        for (int i = 0; i < 5; i++) {
            string2 = string2 + string2;
        }
        addParagraphWithTwoParts1(doc, font, "0123", string2, fontSize);
        doc.add(new Paragraph("Test 1"));
        addParagraphWithTwoParts2(doc, font, "0123", string2, fontSize);
        doc.add(new Paragraph("Test 1"));
        doc.add(new Paragraph("Test 1"));
        addParagraphWithTwoParts1(doc, font, "012345", string2, fontSize);
        doc.add(new Paragraph("Test 2"));
        addParagraphWithTwoParts2(doc, font, "012345", string2, fontSize);
        doc.add(new Paragraph("Test 2"));
        addParagraphWithTwoParts1(doc, font, "0123456789012345", string2, fontSize);
        doc.add(new Paragraph("Test 3"));
        doc.add(new Paragraph("Test 3"));
        addParagraphWithTwoParts2(doc, font, "0123456789012345", string2, fontSize);
        doc.add(new Paragraph("Test 3"));
        doc.add(new Paragraph("Test 3"));
        addParagraphWithTwoParts1(doc, font, "012", "0123456789", fontSize);
        doc.add(new Paragraph("Test 4"));
        addParagraphWithTwoParts2(doc, font, "012", "0123456789", fontSize);
        doc.add(new Paragraph("Test 4"));
        addParagraphWithTwoParts1(doc, font, "012345", "01234567890123456789", fontSize);
        doc.add(new Paragraph("Test 5"));
        addParagraphWithTwoParts2(doc, font, "012345", "01234567890123456789", fontSize);
        doc.add(new Paragraph("Test 5"));
        addParagraphWithTwoParts1(doc, font, "0", "01234567890123456789012345678901234567890123456789", fontSize);
        doc.add(new Paragraph("Test 6"));
        addParagraphWithTwoParts2(doc, font, "0", "01234567890123456789012345678901234567890123456789", fontSize);

        doc.close();
    }

    public void addParagraphWithTwoParts1(Document doc, PdfFont font, String string1, String string2, float fontSize) {
        if (string1 == null) string1 = "";
        if (string1.length() > 10)
            string1 = string1.substring(0, 10);
        Text chunk1 = new Text(string1).setFont(font).setFontSize(fontSize);
        if (string2 == null) string2 = "";
        if (string1.length() + string2.length() > 100)
            string2 = string2.substring(0, 100 - string1.length());
        Text chunk2 = new Text(string2).setFont(font).setFontSize(fontSize);
        Paragraph p = new Paragraph();
        p.add(chunk1);
        // p.add(GLUE);
        p.add(chunk2);
        // TODO There is no LineSeparator & we cannot draw line because of absence of its position before rendering
        // LineSeparator line = new LineSeparator();
        // line.setOffset(-2);
        // p.add(line);
        doc.add(p);
    }

    public void addParagraphWithTwoParts2(Document doc, PdfFont font, String string1, String string2, float fontSize) {
        if (string1 == null) string1 = "";
        if (string1.length() > 10)
            string1 = string1.substring(0, 10);
        if (string2 == null) string2 = "";
        if (string1.length() + string2.length() > 100)
            string2 = string2.substring(0, 100 - string1.length());
        Paragraph p = new Paragraph(string1 + " " + string2).setFont(font).setFontSize(fontSize);
        // TODO There is no LineSeparator & we cannot draw line because of absence of its position before rendering
        // LineSeparator line = new LineSeparator();
        // line.setOffset(-2);
        // p.add(line);
        doc.add(p);
    }
}
