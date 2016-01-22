package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddLinkAnnotation extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/primes.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_link_annotation.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLinkAnnotation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        Rectangle linkLocation = new Rectangle(523, 770, 559 - 523, 806 - 770);
        int[] borders = {0, 0, 1};
        PdfArray array = new PdfArray();
        array.add(pdfDoc.getPage(3).getPdfObject());
        array.add(PdfName.Fit);
        PdfDestination destination = PdfDestination.makeDestination(array);
        PdfAnnotation annotation = new PdfLinkAnnotation(pdfDoc, linkLocation)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT)
                .setAction(PdfAction.createGoTo(pdfDoc, destination))
                .setBorder(new PdfArray(borders))
                .setColor(new PdfArray(new float[]{0, 0, 0}));
        pdfDoc.getPage(1).addAnnotation(annotation);
        pdfDoc.close();
    }
}
