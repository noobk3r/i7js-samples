/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/24568386/set-baseurl-of-an-existing-pdf-document
 */
package com.itextpdf.samples.sandbox.interactive;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class BaseURL2 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/interactive/base_url2.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new BaseURL2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        PdfDictionary uri = new PdfDictionary();
        uri.put(PdfName.Type, PdfName.URI);
        uri.put(new PdfName("Base"), new PdfString("http://itextpdf.com/"));
        pdfDoc.getCatalog().put(PdfName.URI, uri);
        PdfAction action = PdfAction.createURI(pdfDoc, "index.php", false);
        Link link = new Link("Home page", action);
        doc.add(new Paragraph(link));

        pdfDoc.close();
    }
}
