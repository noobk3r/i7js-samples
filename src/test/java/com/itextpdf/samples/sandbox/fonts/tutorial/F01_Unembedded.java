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
        doc.add(new Paragraph("Vous êtes d'où?"));
        doc.add(new Paragraph("À tout à l'heure. À bientôt."));
        doc.add(new Paragraph("Je me présente."));
        doc.add(new Paragraph("C'est un étudiant."));
        doc.add(new Paragraph("Ça va?"));
        doc.add(new Paragraph("Il est ingénieur. Elle est médecin."));
        doc.add(new Paragraph("C'est une fenêtre."));
        doc.add(new Paragraph("Répétez, s'il vous plaît."));
        pdfDoc.close();
    }
}
