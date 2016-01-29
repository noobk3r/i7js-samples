/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/31240828/how-navigate-one-page-to-another-page-in-pdf-file-by-using-itextsharp-c
 */
package com.itextpdf.samples.sandbox.stamper;

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
import com.itextpdf.layout.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

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
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        int[] borders = {0, 0, 1};
        PdfArray array = new PdfArray();
        array.add(doc.getPdfDocument().getPage(10).getPdfObject());
        array.add(PdfName.Fit);
        PdfDestination d1 = PdfDestination.makeDestination(array);
        Rectangle rect = new Rectangle(0, 806, 595, 842 - 806);
        PdfAnnotation a10 = new PdfLinkAnnotation(rect)
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
        PdfAnnotation a1 = new PdfLinkAnnotation(rect)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_PUSH)
                .setPage(pdfDoc.getPage(1))
                .setAction(PdfAction.createGoTo(d2))
                .setBorder(new PdfArray(borders))
                .setColor(new PdfArray(new float[]{0, 1, 0}));
        pdfDoc.getPage(10).addAnnotation(a1);
        doc.close();
    }
}
