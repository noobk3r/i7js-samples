package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class LargeImage1 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/images/large_image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/images/large_image1.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new LargeImage1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        Rectangle rect = pdfDoc.getPage(1).getPageSize();
        if (rect.getWidth() < 14400 && rect.getHeight() < 14400) {
            System.out.println("The size of the PDF document is within the accepted limits");
            System.exit(0);
        }
        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgRef);
        PdfImageXObject imgObject = new PdfImageXObject(imgStream);
//        imgObject.setHeight(400);
//        imgObject.setWidth(400);
//        imgObject.put(PdfName.Width, new PdfNumber(400));
//        imgObject.put(PdfName.Height, new PdfNumber(400));

        Image image = new Image(imgObject);
        image.scaleToFit(14400, 14400);
        image.setFixedPosition(0, 0);

        pdfDoc.close();
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc,
                new PageSize(image.getImageWidth(), // * (Float) image.getProperty(Property.HORIZONTAL_SCALING),
                        image.getImageHeight())); // * (Float) image.getProperty(Property.VERTICAL_SCALING)));
        // TODO Add all streams to document in order to render it well
        doc.add(image);

//        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
//        canvas.addXObject(image.getXObject(), 0, 0, 14400, false);
//        canvas.stroke();
        doc.close();

    }
}
