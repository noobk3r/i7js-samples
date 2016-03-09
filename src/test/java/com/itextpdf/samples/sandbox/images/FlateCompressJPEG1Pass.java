/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This sample is written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/21958449/can-itextsharp-generate-pdf-with-jpeg-images-that-are-multi-stage-filtered-both
 *
 * The question was about adding compression to an image that already used /DCTDecode
 *
 * IMPORTANT:
 * This sample uses kernel iText functionality that was written in answer to the question.
 * This example will only work starting with iText 5.5.1
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class FlateCompressJPEG1Pass extends GenericTest {
    public static final String IMAGE = "./src/test/resources/img/berlin2013.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/flate_compress_jpeg_1pass.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FlateCompressJPEG1Pass().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Image image = new Image(ImageFactory.getImage(IMAGE));
        image.getXObject().getPdfObject().setCompressionLevel(PdfOutputStream.BEST_COMPRESSION);
        image.scaleToFit(PageSize.A4.rotate().getWidth(), PageSize.A4.rotate().getHeight());
        image.setFixedPosition(0, 0);
        doc.add(image);
        doc.close();
    }
}
