package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;


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

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc2 = new PdfDocument(writer);

        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgRef);
        //create instance for new document
        PdfImageXObject imgObject = new PdfImageXObject((PdfStream) imgStream.copyToDocument(pdfDoc2));

        Image image = new Image(imgObject);

        image.scaleToFit(14400, 14400);
        image.setFixedPosition(0, 0);
        pdfDoc.close();

        Document doc = new Document(pdfDoc2,
                new PageSize(image.getImageScaledWidth(),
                        image.getImageScaledHeight()));
        doc.add(image);
        doc.close();


    }
}
