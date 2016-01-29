/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_10_16_TransparentImage extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_16_TransparentImage.pdf";
    public static final String RESOURCE1
            = "./src/test/resources/book/part3/chapter10/bruno.jpg";
    public static final String RESOURCE2
            = "./src/test/resources/book/part3/chapter10/info.png";
    public static final String RESOURCE3
            = "./src/test/resources/book/part3/chapter10/1t3xt.gif";
    public static final String RESOURCE4
            = "./src/test/resources/book/part3/chapter10/logo.gif";

    public static void main(String args[]) throws IOException {
        new Listing_10_16_TransparentImage().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        Image img1 = new Image(ImageFactory.getImage(RESOURCE1));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(img1.getImageScaledWidth(), img1.getImageScaledHeight()));
        img1.setFixedPosition(0, 0);
        doc.add(img1);
        Image img2 = new Image(ImageFactory.getImage(RESOURCE2));
        img2.setFixedPosition(0, 260);
        doc.add(img2);
        com.itextpdf.io.image.Image img3basics = ImageFactory.getImage(RESOURCE3);
        img3basics.setTransparency(new int[]{0x00, 0x10});
        Image img3 = new Image(img3basics);
        img3.setFixedPosition(0, 0);
        doc.add(img3);
        com.itextpdf.io.image.Image img4basics = ImageFactory.getImage(RESOURCE4);
        img4basics.setTransparency(new int[]{0xF0, 0xFF});
        Image img4 = new Image(img4basics);
        img4.setFixedPosition(50, 50);
        doc.add(img4);
        doc.close();
    }
}
