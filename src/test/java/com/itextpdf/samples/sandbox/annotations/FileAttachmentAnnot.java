/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/31006683/itext-clickable-image-should-open-ms-word-attachment
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class FileAttachmentAnnot extends GenericTest {
    public static final String IMG = "./src/test/resources/img/info.png";
    public static final String PATH = "./src/test/resources/txt/test.docx";
    public static final String DEST = "./target/test/resources/sandbox/annotations/file_attachment_annot.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FileAttachmentAnnot().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Rectangle rect = new Rectangle(36, 700, 100, 100);
        PdfFileSpec fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, PATH, null, "test.docx", null, null, false);
        PdfAnnotation attachment = new PdfFileAttachmentAnnotation(rect, fs)
                .setContents("Click me");

        PdfFormXObject xObject = new PdfFormXObject(rect);
        ImageData img = ImageFactory.getImage(IMG);
        com.itextpdf.layout.element.Image imgModel = new com.itextpdf.layout.element.Image(img);
        imgModel.scaleAbsolute(100, 100);
        imgModel.setFixedPosition(0, 0);
        PdfCanvas canvas = new PdfCanvas(xObject, pdfDoc);
        canvas.addImage(img, rect, true);
        attachment.setNormalAppearance(xObject.getPdfObject());

        pdfDoc.addNewPage().addAnnotation(attachment);
        pdfDoc.close();
    }
}
