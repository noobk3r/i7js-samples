/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19976343/how-to-set-the-paragraph-of-itext-pdf-file-as-rectangle-with-background-color-in
 *
 * We create a table with two columns and two cells.
 * This way, we can add two images next to each other.
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
public class ChunkBackground extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/chunk_background.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChapterAndTitle().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        PdfFont f = PdfFont.createFont(pdfDoc, FontConstants.TIMES_BOLD, "WinAnsi");
        Text c = new Text("White text on red background")
                .setFont(f)
                .setFontSize(25.0f)
                .setFontColor(Color.WHITE)
                .setBackgroundColor(Color.RED);
        Paragraph p = new Paragraph(c);
        doc.add(p);

        doc.close();
    }
}
