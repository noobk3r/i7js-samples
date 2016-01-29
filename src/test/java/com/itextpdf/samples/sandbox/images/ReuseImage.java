/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.images;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ReuseImage extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/images/single_image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/images/reuse_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReuseImage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC));
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc2 = new PdfDocument(writer);

        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgRef);
        PdfImageXObject imgObject = new PdfImageXObject((PdfStream) imgStream.copyToDocument(pdfDoc2));
        pdfDoc.close();
        Image image = new Image(imgObject);
        image.scaleToFit(842, 595);
        image.setFixedPosition((842 -
                        image.getImageWidth() * (Float) image.getProperty(Property.HORIZONTAL_SCALING)) / 2,
                (595 - image.getImageHeight() * (Float) image.getProperty(Property.VERTICAL_SCALING)) / 2);
        image.setWidth(image.getImageWidth() * (Float) image.getProperty(Property.HORIZONTAL_SCALING));
        image.setHeight(image.getImageHeight() * (Float) image.getProperty(Property.VERTICAL_SCALING));

        Document doc = new Document(pdfDoc2, PageSize.A4.rotate());
        doc.add(image);
        doc.close();
    }
}
