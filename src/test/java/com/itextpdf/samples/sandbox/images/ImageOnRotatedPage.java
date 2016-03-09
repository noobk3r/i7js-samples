/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/33787785/itext-rotate-pdf-document-but-not-image
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class ImageOnRotatedPage extends GenericTest {
    public static final String IMAGE = "./src/test/resources/img/cardiogram.png";
    public static final String DEST = "./target/test/resources/sandbox/images/image_on_rotated_page.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ImageOnRotatedPage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());
        Image img = new Image(ImageFactory.getImage(IMAGE));
        img.scaleToFit(770, 523);
        float offsetX = (770 - img.getImageScaledWidth()) / 2;
        float offsetY = (523 - img.getImageScaledHeight()) / 2;
        img.setFixedPosition(36 + offsetX, 36 + offsetY);
        doc.add(img);
        doc.close();
    }
}
