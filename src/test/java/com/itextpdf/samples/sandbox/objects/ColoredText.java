/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27578497/applying-color-to-strings-in-paragraph-using-itext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
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

        // TODO Implement setNormal() on PdfFont objects
        Text redText = new Text("This text is red. ")
                .setFontColor(Color.RED)
                .setFontSize(12)
                .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA));
        Text blueText = new Text("This text is blue and bold")
                .setFontColor(Color.BLUE)
                .setFontSize(12)
                .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD));
        Text greenText = new Text("This text is green and italic. ")
                .setFontColor(Color.GREEN)
                .setFontSize(12)
                .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA))
                .setItalic();
        Paragraph p1 = new Paragraph(redText);
        doc.add(p1);
        Paragraph p2 = new Paragraph();
        p2.add(blueText);
        p2.add(greenText);
        doc.add(p2);
        // TODO There is no ColumnText in itex6, but Document.add isn't quite the same
        doc.showTextAligned(p1.setWidth(144 - 36).setHeight(760 - 600), 36, 480, 1,
                Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        doc.showTextAligned(p2.setWidth(144 - 36).setHeight(760 - 600), 36, 460, 1,
                Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);

        doc.close();
    }
}
