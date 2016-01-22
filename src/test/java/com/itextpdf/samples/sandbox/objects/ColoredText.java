/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27578497/applying-color-to-strings-in-paragraph-using-itext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ColoredText extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/colored_text.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColoredText().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        Text redText = new Text("This text is red. ")
                .setFontColor(Color.RED)
                .setFontSize(12)
                .setFont(PdfFont.createStandardFont(FontConstants.HELVETICA));
        Text blueText = new Text("This text is blue and bold. ")
                .setFontColor(Color.BLUE)
                .setFontSize(12)
                .setFont(PdfFont.createStandardFont(FontConstants.HELVETICA_BOLD));
        Text greenText = new Text("This text is green and italic. ")
                .setFontColor(Color.GREEN)
                .setFontSize(12)
                .setFont(PdfFont.createStandardFont(FontConstants.HELVETICA_OBLIQUE));

        Paragraph p1 = new Paragraph(redText).setMargin(0);
        doc.add(p1);
        Paragraph p2 = new Paragraph().setMargin(0);
        p2.add(blueText);
        p2.add(greenText);
        doc.add(p2);

        new Canvas(new PdfCanvas(pdfDoc.getLastPage()), pdfDoc, new Rectangle(36, 600, 108, 160)).
            add(p1).add(p2);

        doc.close();
    }
}
