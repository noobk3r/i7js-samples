/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27080565/itext-make-a-single-letter-bold-within-a-word
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ColoredLetters extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/colored_letters.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColoredLetters().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph();
        String s = "all text is written in red, except the letters b and g; they are written in blue and green.";
        for (int i = 0; i < s.length(); i++) {
            p.add(returnCorrectColor(pdfDoc, s.charAt(i)));
        }
        doc.add(p);
        
        doc.close();
    }

    private Text returnCorrectColor(PdfDocument pdfDoc, char letter) throws IOException {
        if (letter == 'b') {
            return new Text("b")
                    .setFontColor(Color.BLUE)
                    .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD))
                    .setFontSize(12);
        } else if (letter == 'g') {
            return new Text("g")
                    .setFontColor(Color.GREEN)
                    .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA))
                    .setFontSize(12)
                    .setItalic();
        }
        return new Text(String.valueOf(letter))
                .setFontColor(Color.RED)
                .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA));
    }
}
