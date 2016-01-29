/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.io.image.Image;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.List;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ListWithImageAsBullet extends GenericTest {
    public static final String IMG = "src/test/resources/img/bulb.gif";
    public static final String DEST = "./target/test/resources/sandbox/objects/list_with_image_bullet.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ListWithImageAsBullet().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Image image = ImageFactory.getImage(IMG);
        PdfImageXObject xObject = new PdfImageXObject(image);
//        image.scaleAbsolute(12, 12);
//        image.setScaleToFitHeight(false);
        List list = new List().
                setListSymbol(new com.itextpdf.model.element.Image(xObject)).
                add("Hello World").
                add("This is a list item with a lot of text. " +
                        "It will certainly take more than one line." +
                        " This shows that the list item is indented and that the image is used as bullet.").
                add("This is a test");
        doc.add(list);

        doc.close();
    }
}
