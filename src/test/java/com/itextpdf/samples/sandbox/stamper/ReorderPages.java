/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/30911216/how-to-re-arrange-pages-in-pdf-using-itext
 */
package com.itextpdf.samples.sandbox.stamper;

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
public class ReorderPages extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/stamper/reorder_pages.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReorderPages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        for (int i = 1; i < 17; i++) {
            doc.add(new Paragraph(String.format("Page %s", i)));
            pdfDoc.addNewPage();
        }
        doc.close();
        int startToc = 13;
        int n = pdfDoc.getNumOfPages();
        // TODO Implement selectPages or its analog
        //pdfDoc.selectPages(String.format("1,%s-%s, 2-%s, %s", startToc, n-1, startToc - 1, n));
        pdfDoc.close();
    }
}
