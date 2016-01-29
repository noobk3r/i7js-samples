/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/29331838/add-multiple-images-into-a-single-pdf-file-with-itext-using-java
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class MultipleImages extends GenericTest {
    public static final String[] IMAGES = {
            "./src/test/resources/sandbox/images/berlin2013.jpg",
            "./src/test/resources/sandbox/images/javaone2013.jpg",
            "./src/test/resources/sandbox/images/map_cic.png"
    };
    public static final String DEST =
            "./target/test/resources/sandbox/images/multiple_images.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MultipleImages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Image image = new Image(ImageFactory.getImage(IMAGES[0]));
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(image.getImageWidth(), image.getImageHeight()));
        // TODO why not to add mechanically on the right page
        for (int i = 0; i < IMAGES.length; i++) {
            image = new Image(ImageFactory.getImage(IMAGES[i]));
            pdfDoc.addNewPage(new PageSize(image.getImageWidth(), image.getImageHeight()));
            image.setFixedPosition(i + 1, 0, 0);
            doc.add(image);
        }
        doc.close();
    }
}
