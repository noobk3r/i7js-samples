/**
 * These examples are written by Bruno Lowagie in the context of an article about fonts.
 */
package com.itextpdf.samples.sandbox.fonts.tutorial;

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
public class F01_Unembedded extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/tutorial/f01_unembedded.pdf";


    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new F01_Unembedded().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Vous \u00EAtes d\'o\u00F9?"));
        doc.add(new Paragraph("\u00C0 tout \u00E0 l\'heure. \u00C0 bient\u00F4t."));
        doc.add(new Paragraph("Je me pr\u00E9sente."));
        doc.add(new Paragraph("C\'est un \u00E9tudiant."));
        doc.add(new Paragraph("\u00C7a va?"));
        doc.add(new Paragraph("Il est ing\u00E9nieur. Elle est m\u00E9decin."));
        doc.add(new Paragraph("C\'est une fen\u00EAtre."));
        doc.add(new Paragraph("R\u00E9p\u00E9tez, s\'il vous pla\u00EEt."));
        pdfDoc.close();
    }
}
