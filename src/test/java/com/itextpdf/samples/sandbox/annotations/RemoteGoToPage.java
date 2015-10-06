/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19999048/how-to-create-hyperlink-from-a-pdf-to-another-pdf-to-a-specified-page-using-itex
 *
 * Creating a link from one PDF to another
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
public class RemoteGoToPage extends GenericTest {
    // !IMPORTANT We change the order of SRC and DEST because we want to check via CompareTool
    // comprehensive file rather then simple
    // So DEST file DOES NOT mean destination but DEST-file-which-we-will-check-from-GenericTest
    public static final String SRC = "./target/test/resources/sandbox/annotations/subdir/xyz2.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/remote_go_to_page.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoteGoToPage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        RemoteGoToPage app = new RemoteGoToPage();
        app.createPdf(SRC);
        app.createPdf2(DEST);
    }


    private void createPdf(String src) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(src)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("page 1"));
        pdfDoc.addNewPage();
        doc.add(new Paragraph("page 2"));
        pdfDoc.addNewPage();
        doc.add(new Paragraph("page 3"));
        pdfDoc.addNewPage();
        doc.add(new Paragraph("page 4"));
        pdfDoc.addNewPage();
        doc.add(new Paragraph("page 5"));
        pdfDoc.addNewPage();
        doc.add(new Paragraph("page 6"));
        pdfDoc.addNewPage();
        doc.add(new Paragraph("page 7"));
        pdfDoc.close();
    }

    private void createPdf2(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Paragraph chunk = new Paragraph("Link");
        // TODO Implement Paragraph#setAction(PdfAction) (we don't know paragraph's position until rendering so making invisible PdfAnnotation isn't a solution)
        // chunk.setAction(new PdfAction("subdir/abc2.pdf", 6));
        new Document(pdfDoc).add(chunk);
        pdfDoc.close();
    }
}
