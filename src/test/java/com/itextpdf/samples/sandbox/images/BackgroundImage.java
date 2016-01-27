/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/21731027/backgroundimage-in-landscape-and-cover-whole-pdf-with-itextsharp
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class BackgroundImage extends GenericTest {
    public static final String IMAGE = "./src/test/resources/sandbox/images/berlin2013.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/background_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new BackgroundImage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.addImage(ImageFactory.getImage(IMAGE), PageSize.A4.rotate(), false);
        doc.add(new Paragraph("Berlin!"));
        doc.close();
    }
}
