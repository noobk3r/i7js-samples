/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/22382717/write-two-itext-paragraphs-on-the-same-position
 *
 * We create a Chunk and add a background color.
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class UnderlineWithDottedLine extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/underline_with_dotted_line.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new UnderlineWithDottedLine().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("This line will be underlined with a dotted line.");
        // TODO There is no DottedLineSeparator & we cannot simply draw dotted line because of absence of its position before rendering
        // DottedLineSeparator dottedline = new DottedLineSeparator();
        // dottedline.setOffset(-2);
        // dottedline.setGap(2f);
        // p.add(dottedline);
        doc.add(p);

        doc.close();
    }
}
