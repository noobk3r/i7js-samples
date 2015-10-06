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
public class RemoteGoto extends GenericTest {
    // !IMPORTANT We change the order of SRC and DEST because we want to check via CompareTool
    // comprehensive file rather then simple
    // So DEST file DOES NOT mean destination but DEST-file-which-we-will-check-from-GenericTest
    public static final String SRC = "./target/test/resources/sandbox/annotations/subdir/xyz.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/relative_link.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoteGoto().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        RemoteGoto app = new RemoteGoto();
        app.createPdf(SRC);
        app.createPdf2(DEST);
    }

    private void createPdf(String src) throws Exception{
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(src)));
        Document doc = new Document(pdfDoc);
        // TODO There is no Anchor (we don't know paragraph's position until rendering so making invisible PdfAnnotation isn't a solution)
//        Anchor anchor = new Anchor("This is a destination");
//        anchor.setName("dest");
//        document1.add(anchor);
        pdfDoc.close();
    }

    private void createPdf2(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);
        Paragraph chunk = new Paragraph("Link");
        // TODO Implement Paragraph#setAction(PdfAction) (we don't know paragraph's position until rendering so making invisible PdfAnnotation isn't a solution)
//        chunk.setAction(PdfAction.gotoRemotePage("subdir/abc.pdf", "dest", false, true));
//        document2.add(chunk);
        pdfDoc.close();
    }
}
