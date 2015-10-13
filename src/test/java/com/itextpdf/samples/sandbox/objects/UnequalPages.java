/**
 * This example is written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/23117200/itext-create-document-with-unequal-page-sizes
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class UnequalPages extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/unequal_pages.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new UnequalPages().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Rectangle one = new Rectangle(70, 140);
        Rectangle two = new Rectangle(700, 400);
        // TODO Bug with setMargins
        pdfDoc.addNewPage(new PageSize(one).setMargins(2, 2, 2, 2));
        Paragraph p = new Paragraph("Hi");
        doc.add(p);
        pdfDoc.addNewPage(new PageSize(two).setMargins(20, 20, 20, 20));
        doc.add(new AreaBreak());
        doc.add(p);

        doc.close();
    }
}
