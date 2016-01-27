/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class LargeImage2 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/images/large_image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/images/large_image2.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new LargeImage2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(new FileInputStream(SRC));
        reader.setCloseStream(false);
        PdfDocument pdfDoc = new PdfDocument(reader);
        Rectangle rect = pdfDoc.getPage(1).getPageSize();

        File tmp = File.createTempFile("large_image", ".pdf", new File("."));
        tmp.deleteOnExit();
        // TODO problem with double opening of reader
        PdfDocument pdfDoc2 = new PdfDocument(reader, new PdfWriter(new FileOutputStream(tmp)));

        if (rect.getWidth() < 14400 && rect.getHeight() < 14400) {
            System.out.println("The size of the PDF document is within the accepted limits");
            System.exit(0);
        }

        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgRef);
        PdfImageXObject imgObject = new PdfImageXObject((PdfStream) imgStream.copyToDocument(pdfDoc2));
        pdfDoc.close();

        Image image = new Image(imgObject);
        image.scaleToFit(14400, 14400);
        image.setFixedPosition(0, 0);


        pdfDoc2.addNewPage(1,
                new PageSize(image.getImageWidth() * (Float) image.getProperty(Property.HORIZONTAL_SCALING),
                        image.getImageHeight() * (Float) image.getProperty(Property.VERTICAL_SCALING)));
        new PdfCanvas(pdfDoc2.getFirstPage()).addXObject(image.getXObject(), 0, 0);
        pdfDoc2.close();
        reader.close();
        // We create a new file that only contains the new first page
        reader = new PdfReader(tmp.getAbsolutePath());
        pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));
        pdfDoc.close();
        reader.close();

    }
}
