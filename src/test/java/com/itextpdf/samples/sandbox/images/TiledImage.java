/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26859473/how-to-show-an-image-with-large-dimensions-across-multiple-pages-in-itextpdf
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TiledImage extends GenericTest {
    public static final String IMAGE = "./src/test/resources/img/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/tiled_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TiledImage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        ImageData image = ImageFactory.getImage(IMAGE);
        float width = image.getWidth();
        float height = image.getHeight();
        Rectangle page = new Rectangle(width / 2, height / 2);

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(page));
        PdfCanvas canvas;
        canvas = new PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(),
                pdfDoc.getLastPage().getResources(), pdfDoc);
        canvas.addImage(image, width, 0, 0, height, 0, -height / 2, false);
        canvas = new PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(),
                pdfDoc.getLastPage().getResources(), pdfDoc);
        canvas.addImage(image, width, 0, 0, height, 0, 0, false);
        canvas = new PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(),
                pdfDoc.getLastPage().getResources(), pdfDoc);
        canvas.addImage(image, width, 0, 0, height, -width / 2, -height / 2, false);
        canvas = new PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(),
                pdfDoc.getLastPage().getResources(), pdfDoc);
        canvas.addImage(image, width, 0, 0, height, -width / 2, 0, false);
        doc.close();
    }
}
