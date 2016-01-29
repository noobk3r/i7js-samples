/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This sample is written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27070222/how-to-create-unique-images-with-image-getinstance
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class RawImages extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/images/raw_images.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RawImages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Image gray = new Image(ImageFactory.getImage(1, 1, 1, 8,
                new byte[]{(byte) 0x80}, null));
        gray.scaleToFit(30, 30);

        Image red = new Image(ImageFactory.getImage(1, 1, 3, 8,
                new byte[]{(byte) 255, (byte) 0, (byte) 0}, null));
        red.scaleToFit(30, 30);

        Image green = new Image(ImageFactory.getImage(1, 1, 3, 8,
                new byte[]{(byte) 0, (byte) 255, (byte) 0}, null));
        green.scaleToFit(30, 30);

        Image blue = new Image(ImageFactory.getImage(1, 1, 3, 8,
                new byte[]{(byte) 0, (byte) 0, (byte) 255}, null));
        blue.scaleToFit(30, 30);

        Image cyan = new Image(ImageFactory.getImage(1, 1, 4, 8,
                new byte[]{(byte) 255, (byte) 0, (byte) 0, (byte) 0}, null));
        cyan.scaleToFit(30, 30);

        Image magenta = new Image(ImageFactory.getImage(1, 1, 4, 8,
                new byte[]{(byte) 0, (byte) 255, (byte) 0, (byte) 0}, null));
        magenta.scaleToFit(30, 30);

        Image yellow = new Image(ImageFactory.getImage(1, 1, 4, 8,
                new byte[]{(byte) 0, (byte) 0, (byte) 255, (byte) 0}, null));
        yellow.scaleToFit(30, 30);

        Image black = new Image(ImageFactory.getImage(1, 1, 4, 8,
                new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 255}, null));
        black.scaleToFit(30, 30);

        doc.add(gray);
        doc.add(red);
        doc.add(green);
        doc.add(blue);
        doc.add(cyan);
        doc.add(magenta);
        doc.add(yellow);
        doc.add(black);

        doc.close();
    }
}
