/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/30876926/itext-std-deviation-symbol-%CF%83-not-printing
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
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
public class StandardDeviation extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/standard_deviation.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ShadedFill().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("The standard deviation symbol doesn't exist in Helvetica."));
        PdfFont symbol = PdfFont.createStandardFont(FontConstants.SYMBOL);
        Paragraph p = new Paragraph("So we use the Symbol font: ");
        p.add(new Text("s").setFont(symbol));
        doc.add(p);

        doc.close();
    }
}
