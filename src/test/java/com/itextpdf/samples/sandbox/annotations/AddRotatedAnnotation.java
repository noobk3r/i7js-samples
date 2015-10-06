/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27083206/itextshape-clickable-polygon-or-path
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.core.pdf.annot.PdfStampAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddRotatedAnnotation extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_rotated_annotation.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddRotatedAnnotation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));

        PdfAction action = PdfAction.createURI(pdfDoc, "http://pages.itextpdf.com/ebook-stackoverflow-questions.html");
        Rectangle linkLocation1 = new Rectangle(30, 770, 120 - 30, 800 - 770);
        // TODO there is no default border in PdfLinkAnnotation. In comparison, there is in itext5
        PdfLinkAnnotation link1 = new PdfLinkAnnotation(pdfDoc, linkLocation1)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT)
                .setAction(action)
                .setColor(Color.RED.getColorValue())
                .setBorder(new PdfArray(new float[]{0, 0, 1}));
        pdfDoc.getFirstPage().addAnnotation(link1);

        Rectangle linkLocation2 = new Rectangle(30, 670, 60 - 30, 760 - 670);
        PdfLinkAnnotation link2 = new PdfLinkAnnotation(pdfDoc, linkLocation2)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT)
                .setAction(action)
                .setColor(Color.GREEN.getColorValue())
                .setBorder(new PdfArray(new float[]{0, 0, 1}));
        pdfDoc.getFirstPage().addAnnotation(link2);

        Rectangle linkLocation3 = new Rectangle(150, 770, 240 - 150, 800 - 770);
        PdfStampAnnotation stamp1 = new PdfStampAnnotation(pdfDoc, linkLocation3)
                .setStampName(new PdfName("Confidential"))
                .setContents("Landscape");
        pdfDoc.getFirstPage().addAnnotation(stamp1);

        Rectangle linkLocation4 = new Rectangle(150, 670, 240 - 150, 760 - 670);
        PdfStampAnnotation stamp2 = new PdfStampAnnotation(pdfDoc, linkLocation4)
                .setStampName(new PdfName("Confidential"))
                .setContents("Portrait")
                .put(PdfName.Rotate, new PdfNumber(90));
        pdfDoc.getFirstPage().addAnnotation(stamp2);

        Rectangle linkLocation5 = new Rectangle(250, 670, 340 - 250, 760 - 670);
        PdfStampAnnotation stamp3 = new PdfStampAnnotation(pdfDoc, linkLocation5)
                .setStampName(new PdfName("Confidential"))
                .setContents("Portrait")
                .put(PdfName.Rotate, new PdfNumber(45));
        pdfDoc.getFirstPage().addAnnotation(stamp3);

        pdfDoc.close();
    }
}
