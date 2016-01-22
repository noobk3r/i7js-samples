/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24220668/fontfactory-lowagie-java-getting-java-io-eofexception-when-trying-to-use-gre
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
public class LiberationSans extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/liberation_sans.pdf";
    public static final String FONT = "./src/test/resources/sandbox/fonts/LiberationSans-Regular.ttf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new LiberationSans().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfFont.register(FONT, "Greek-Regular");
        PdfFont f = PdfFont.createRegisteredFont("Greek-Regular", "CP1253", true);
        Paragraph p = new Paragraph("\u039d\u03cd\u03c6\u03b5\u03c2").setFont(f);
        doc.add(p);
        pdfDoc.close();
    }
}
