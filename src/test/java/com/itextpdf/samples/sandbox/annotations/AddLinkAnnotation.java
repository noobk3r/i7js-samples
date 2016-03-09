/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddLinkAnnotation extends GenericTest {
    public static final String SRC = "./src/test/resources/pdfs/primes.pdf";
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
        PdfAnnotation annotation = new PdfLinkAnnotation(linkLocation)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT)
                .setAction(PdfAction.createGoTo(destination))
                .setBorder(new PdfArray(borders))
                .setColor(new PdfArray(new float[]{0, 0, 0}));
        pdfDoc.getPage(1).addAnnotation(annotation);
        pdfDoc.close();
    }
}
