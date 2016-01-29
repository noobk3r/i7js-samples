/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.io.image.Jbig2Image;
import com.itextpdf.io.image.TiffImage;
import com.itextpdf.io.source.RandomAccessFileOrArray;
import com.itextpdf.io.source.RandomAccessSourceFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_10_14_PagedImages extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part3/chapter10/Listing_10_14_PagedImages.pdf";
    public static final String RESULT
            = "./target/test/resources/book/part3/chapter10/tiff_jbig2_gif.pdf";
    public static final String RESOURCE1
            = "./src/test/resources/book/part3/chapter10/marbles.tif";
    public static final String RESOURCE2
            = "./src/test/resources/book/part3/chapter10/amb.jb2";
    public static final String RESOURCE3
            = "./src/test/resources/book/part3/chapter10/animated_fox_dog.gif";

    public static void main(String args[]) throws IOException {
        new Listing_10_14_PagedImages().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        addTif(doc, RESOURCE1);
        doc.add(new AreaBreak());
        addJBIG2(doc, RESOURCE2);
        doc.add(new AreaBreak());
        addGif(doc, RESOURCE3);
        doc.close();
    }

    /**
     * Adds the different pages inside a single TIFF file to a document.
     *
     * @param document the document to which the pages have to be added
     * @param path     the path to the TIFF file
     * @throws IOException
     */
    public static void addTif(Document document, String path) throws IOException {
        RandomAccessFileOrArray ra = new RandomAccessFileOrArray(
                new RandomAccessSourceFactory().createSource(new File(path).toURI().toURL()));
        int n = TiffImage.getNumberOfPages(ra);
        Image img;
        for (int i = 1; i <= n; i++) {
            img = new Image(ImageFactory.getTiffImage(new File(path).toURI().toURL(), false, i, true));
            img.scaleToFit(523, 350);
            document.add(img);
        }
    }

    /**
     * Adds the different pages inside a JBIG2 file to a document.
     *
     * @param document the document to which the pages have to be added
     * @param path     the path to the JBIG2 file
     * @throws IOException
     */
    public static void addJBIG2(Document document, String path) throws IOException {
        RandomAccessFileOrArray ra = new RandomAccessFileOrArray(
                new RandomAccessSourceFactory().createSource(new File(path).toURI().toURL()));
        int n = Jbig2Image.getNumberOfPages(ra);
        Image img;
        for (int i = 1; i <= n; i++) {
            img = new Image(ImageFactory.getJbig2Image(new File(path).toURI().toURL(), i));
            img.scaleToFit(523, 350);
            document.add(img);
        }
    }

    /**
     * Adds the different frames inside an animated GIF to a document.
     *
     * @param document the document to which the frames have to be added
     * @param path     the path to the GIF file
     * @throws IOException
     */
    public static void addGif(Document document, String path) throws IOException {
        int n = 10;
        Image img;
        for (int i = 1; i <= n; i++) {
            img = new Image(ImageFactory.getGifFrame(new File(path).toURI().toURL(), i));
            document.add(img);
        }
    }
}
