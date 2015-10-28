/**
 * These examples are written by Bruno Lowagie in the context of an article about fonts.
 */
package com.itextpdf.samples.sandbox.fonts.tutorial;

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
public class F02_Unembedded extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/tutorial/f02_unembedded.pdf";


    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new F02_Unembedded().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Odkud jste?"));
        doc.add(new Paragraph("Uvid\u00EDme se za chvilku. M\u011Bj se."));
        doc.add(new Paragraph("Dovolte, abych se p\u0159edstavil."));
        doc.add(new Paragraph("To je studentka."));
        doc.add(new Paragraph("V\u0161echno v po\u0159\u00E1dku?"));
        doc.add(new Paragraph("On je in\u017Een\u00FDr. Ona je l\u00E9ka\u0159."));
        doc.add(new Paragraph("Toto je okno."));
        doc.add(new Paragraph("Zopakujte to pros\u00EDm."));
        pdfDoc.close();
    }
}
