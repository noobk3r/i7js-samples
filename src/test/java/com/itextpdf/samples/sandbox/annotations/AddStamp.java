/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/29229629/how-to-add-a-printable-or-non-printable-bitmap-stamp-to-a-pdf
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class AddStamp extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String IMG = "./src/test/resources/sandbox/annotations/itext.png";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_stamp.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddStamp().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));

        Image img = new Image(ImageFactory.getImage(IMG));
        float w = img.getImageScaledWidth();
        float h = img.getImageScaledHeight();
        Rectangle location = new Rectangle(36, 770 - h, w, h);
        PdfStampAnnotation stamp = new PdfStampAnnotation(location)
                .setStampName(new PdfName("ITEXT"));
        img.setFixedPosition(0, 0);
        PdfCanvas cb = new PdfCanvas(pdfDoc.getFirstPage());
        // TODO There is no PdfCanvas#createAppearance()
        // PdfAnnotationAppearance app = new PdfAnnotationAppearance();
        // app.addImage(img);
        // stamp.setAppearance(PdfName.N, app);
        stamp.setFlags(PdfAnnotation.Print);

        pdfDoc.getFirstPage().addAnnotation(stamp);
        pdfDoc.close();
    }
}
