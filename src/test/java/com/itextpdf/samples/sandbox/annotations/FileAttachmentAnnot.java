/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/31006683/itext-clickable-image-should-open-ms-word-attachment
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.annot.PdfFileAttachmentAnnotation;
import com.itextpdf.core.pdf.filespec.PdfFileSpec;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FileAttachmentAnnot extends GenericTest {
    public static final String IMG = "./src/test/resources/sandbox/annotations/info.png";
    public static final String PATH = "./src/test/resources/sandbox/annotations/test.docx";
    public static final String DEST = "./target/test/resources/sandbox/annotations/file_attachment_annot.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FileAttachmentAnnot().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Rectangle rect = new Rectangle(36, 700, 136, 800);
        PdfFileSpec fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, PATH, null, "test.docx", null, null, false);
        PdfFileAttachmentAnnotation attachment = new PdfFileAttachmentAnnotation(rect, fs)
                .setContents("Click me");
        // TODO There is no PdfCanvas#createAppearance()
        //PdfAnnotationAppearance app = writer.getDirectContent().createAppearance(100, 100);
        Image img = new Image(ImageFactory.getImage(IMG));
        img.scaleAbsolute(100, 100);
        img.setFixedPosition(0, 0);
        // app.addImage(img);
        //attachment.setAppearance(PdfName.N, app);
        pdfDoc.getFirstPage().addAnnotation(attachment);
        pdfDoc.close();
    }
}
