/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/29275194/change-pdf-fillcolor-annotation-property-using-itextsharp-c-sharp
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfCircleAnnotation;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class CircleAnnotation extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/circle_annotation.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CircleAnnotation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        Rectangle rect = new Rectangle(150, 770, 200 - 150, 820 - 770);
        PdfCircleAnnotation annotation = new PdfCircleAnnotation(rect)
                .setContents("Circle")
                .setTitle(new PdfString("Circle"))
                .setColor(Color.BLUE)
                .setFlags(PdfAnnotation.Print)
                        // TODO Find way to use DashBorder here
                .setBorder(new PdfArray(new float[]{0, 0, 2})) // new PdfBorderArray(0, 0, 2, new PdfDashPattern()));
                .put(PdfName.IC, new PdfArray(new int[]{1, 0, 0}));
        pdfDoc.getFirstPage().addAnnotation(annotation);
        pdfDoc.close();
    }
}
