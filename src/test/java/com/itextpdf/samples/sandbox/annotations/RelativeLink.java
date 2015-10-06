/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27063677/use-of-relative-path-for-anchor-method-using-itext-for-pdf-generation
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class RelativeLink extends GenericTest {
    public static final String XML = "./src/test/resources/sandbox/annotations/data.xml";
    public static final String DEST = "./target/test/resources/sandbox/annotations/relative_link.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RelativeLink().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Paragraph chunk = new Paragraph("Click me");
        // TODO Implement setAnchor (we don't know paragraph's position until rendering so making invisible PdfAnnotation isn't a solution)
        //chunk.setAnchor("./" + new File(XML).getName());
        doc.add(chunk);

        pdfDoc.close();
    }
}
