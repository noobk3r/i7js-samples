/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/31240828/how-navigate-one-page-to-another-page-in-pdf-file-by-using-itextsharp-c
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddNavigation extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/primes.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_navigation.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddNavigation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        int[] borders = {0, 0, 1};
        PdfArray array = new PdfArray();
        array.add(doc.getPdfDocument().getPage(10).getPdfObject());
        array.add(PdfName.Fit);
        PdfDestination d1 = PdfDestination.makeDestination(array);
        Rectangle rect = new Rectangle(0, 806, 595, 842 - 806);
        PdfAnnotation a10 = new PdfLinkAnnotation(pdfDoc, rect)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT)
                .setPage(pdfDoc.getPage(10))
                .setAction(PdfAction.createGoTo(d1))
                .setBorder(new PdfArray(borders))
                .setColor(new PdfArray(new float[]{0, 1, 0}));
        pdfDoc.getPage(1).addAnnotation(a10);
        array = new PdfArray();
        array.add(doc.getPdfDocument().getPage(1).getPdfObject());
        array.add(PdfName.Fit);
        PdfDestination d2 = PdfDestination.makeDestination(array);
        PdfAnnotation a1 = new PdfLinkAnnotation(pdfDoc, rect)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_PUSH)
                .setPage(pdfDoc.getPage(1))
                .setAction(PdfAction.createGoTo(d2))
                .setBorder(new PdfArray(borders))
                .setColor(new PdfArray(new float[]{0, 1, 0}));
        pdfDoc.getPage(10).addAnnotation(a1);
        pdfDoc.close();
    }
}
