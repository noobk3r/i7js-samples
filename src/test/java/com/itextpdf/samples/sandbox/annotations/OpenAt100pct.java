/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/31314993/how-to-set-a-default-zoom-of-100-using-itext-java
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class OpenAt100pct extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/annotations/open_at_100pct.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new OpenAt100pct().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc, new PageSize(612, 792));
        doc.add(new Paragraph("Hello World"));
        PdfArray array = new PdfArray();
        array.add(pdfDoc.getPage(1).getPdfObject());
        array.add(PdfName.XYZ);
        array.add(new PdfNumber(0));
        array.add(new PdfNumber(842));
        array.add(new PdfNumber(1f));
        PdfDestination pdfDest = PdfDestination.makeDestination(array);
        pdfDoc.getCatalog().setOpenAction(PdfAction.createGoTo(pdfDoc, pdfDest));
        pdfDoc.close();
    }
}
