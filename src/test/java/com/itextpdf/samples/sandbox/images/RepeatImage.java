/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/21720802/how-to-make-text-invisible-in-an-existing-pdf
 *
 * In this example, we take an image that is present in the background,
 * and we add the same image (by its reference) to the foreground so that
 * it covers the OCR'd text.
 */
package com.itextpdf.samples.sandbox.images;

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

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class RepeatImage extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/images/chinese.pdf";
    public static final String DEST = "./target/test/resources/sandbox/images/repeat_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RepeatImage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        PdfDocument pdfDoc2 = new PdfDocument(new PdfWriter(dest));

        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgRef);//
        PdfImageXObject imgObject = new PdfImageXObject((PdfStream) imgStream.copyToDocument(pdfDoc2));

        Image image = new Image(imgObject);
        image.setAutoScale(true);
        image.scaleToFit(pdfDoc.getPage(1).getPageSize().getWidth(), pdfDoc.getPage(1).getPageSize().getHeight());
        pdfDoc.close();

        Document doc = new Document(pdfDoc2);
        doc.add(image);
        doc.close();


    }
}
