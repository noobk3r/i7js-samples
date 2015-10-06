/**
 * This example was written to create a sample file for use in code that answers the following question:
 * http://stackoverflow.com/questions/22828782/all-links-of-existing-pdf-change-the-action-property-to-inherit-zoom-itext-lib
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.navigation.PdfDestination;
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
public class XYZDestination extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/annotations/xyz_destination.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new XYZDestination().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        for (int i = 0; i < 10; i++) {
            doc.add(new Paragraph("Test"));
            pdfDoc.addNewPage();
        }
        PdfDestination d;
        Paragraph c;
        PdfArray array = new PdfArray();
        array.add(PdfName.XYZ);
        array.add(new PdfNumber(36));
        array.add(new PdfNumber(806));
        array.add(new PdfNumber(0));
        for (int i = 0; i < 10; ) {
            array.set(3, new PdfNumber(++i));
            c = new Paragraph("Goto page " + i);
            // TODO Implement setAnnnotation (we don't know paragraph's position until rendering so making invisible PdfAnnotation isn't a solution)
            // c.setAnnotation(PdfAnnotation.createLink(writer, new Rectangle(0, 0),
            //          PdfAnnotation.HIGHLIGHT_NONE, i, d));
            doc.add(c);
        }
        pdfDoc.close();
    }
}
