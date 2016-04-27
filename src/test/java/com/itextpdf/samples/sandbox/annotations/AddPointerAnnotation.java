/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26752663/adding-maps-at-itext-java
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLineAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddPointerAnnotation extends GenericTest {
    public static final String IMG = "./src/test/resources/img/map_cic.png";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_pointer_annotation.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddPointerAnnotation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Image img = new Image(ImageDataFactory.create(IMG));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document document = new Document(pdfDoc, new PageSize(img.getImageWidth(), img.getImageHeight()));
        img.setFixedPosition(0, 0);
        document.add(img);
        Rectangle rect = new Rectangle(220, 350, 475 - 220, 595 - 350);
        // "Cambridge Innovation Center" == contents;
        PdfLineAnnotation lineAnnotation = new PdfLineAnnotation(rect, new float[]{225, 355, 470, 590});
        PdfArray le = new PdfArray();
        le.add(new PdfName("OpenArrow"));
        le.add(new PdfName("None"));
        lineAnnotation.setTitle(new PdfString("You are here:"));
        lineAnnotation.setContents("Cambridge Innovation Center");
        lineAnnotation.setColor(Color.RED);
        lineAnnotation.setFlag(PdfAnnotation.PRINT);
        PdfDictionary borderStyle = new PdfDictionary();
        borderStyle.put(PdfName.S, PdfName.S);
        borderStyle.put(PdfName.W, new PdfNumber(5));
        lineAnnotation.setBorderStyle(borderStyle);
        lineAnnotation.put(new PdfName("LE"), le);
        lineAnnotation.put(new PdfName("IT"), new PdfName("LineArrow"));
        pdfDoc.getFirstPage().addAnnotation(lineAnnotation);
        document.close();
    }
}
