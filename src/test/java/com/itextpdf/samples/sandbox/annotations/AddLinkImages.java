/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/29388313/itext-how-to-associate-actions-with-graphical-object
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.canvas.wmf.WmfImage;
import com.itextpdf.core.pdf.canvas.wmf.WmfImageHelper;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

@Category(SampleTest.class)
public class AddLinkImages extends GenericTest {
    public static final String sourceFolder = "./src/test/resources/img/";
    public static final String INFO = sourceFolder + "info.png";
    public static final String DOG = sourceFolder + "dog.bmp";
    public static final String FOX = sourceFolder + "fox.bmp";
    public static final String BUTTERFLY = sourceFolder + "butterfly.wmf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_link_images.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLinkImages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        Paragraph p = new Paragraph("Objects with links");
        p.add(createImage(INFO, "http://itextpdf.com/", pdfDoc));
        p.add(createImage(DOG, "http://pages.itextpdf.com/ebook-stackoverflow-questions.html", pdfDoc));
        p.add(createImage(FOX, "http://stackoverflow.com/q/29388313/1622493", pdfDoc));
        //@TODO Use Image constructor instead of this ugly construction when PdfDocument is removed from PdfFont constructor(DEVSIX-329)
        p.add(new Image((PdfFormXObject) new WmfImageHelper(new WmfImage(BUTTERFLY)).createPdfForm(pdfDoc)).
                setProperty(Property.ACTION, PdfAction.createURI("http://stackoverflow.com/questions/tagged/itext*")));
        doc.add(p);
        pdfDoc.close();
    }

    public Image createImage(String src, String url, PdfDocument pdfDoc) throws MalformedURLException {
        Image img = new Image(ImageFactory.getImage(src));
        img.setProperty(Property.ACTION, PdfAction.createURI(url));
        return img;
    }
}
