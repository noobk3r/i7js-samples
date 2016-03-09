/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26580912/pdf-convert-to-black-and-white-pngs
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ReplaceImage extends GenericTest {
    public static final String SRC = "./src/test/resources/img/image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/images/replace_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReplaceImage().manipulatePdf(DEST);
    }

    public static Image makeBlackAndWhitePng(PdfImageXObject image) throws IOException {
        BufferedImage bi = image.getBufferedImage();
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return new Image(ImageFactory.getImage(baos.toByteArray()));
    }

    public static void replaceStream(PdfStream orig, PdfStream stream) throws IOException {
        orig.clear();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        stream.setData(baos.toByteArray(), true);
        orig.setData(baos.toByteArray(), false);
        for (PdfName name : stream.keySet()) {
            orig.put(name, stream.get(name));
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
        PdfDictionary page = pdfDoc.getFirstPage().getPdfObject();
        PdfDictionary resources = page.getAsDictionary(PdfName.Resources);
        PdfDictionary xobjects = resources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = xobjects.keySet().iterator().next();
        PdfStream stream = (PdfStream) xobjects.getAsStream(imgRef);
        Image img = makeBlackAndWhitePng(new PdfImageXObject(stream));
        // TODO Investigate the problem
        replaceStream(stream, img.getXObject().getPdfObject());
        pdfDoc.close();
    }
}
