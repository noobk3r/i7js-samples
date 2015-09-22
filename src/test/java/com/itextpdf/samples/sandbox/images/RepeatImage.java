/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/21720802/how-to-make-text-invisible-in-an-existing-pdf
 *
 * In this example, we take an image that is present in the background,
 * and we add the same image (by its reference) to the foreground so that
 * it covers the OCR'd text.
 */
package com.itextpdf.samples.sandbox.images;

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
        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgRef);
//        PdfImageXObject imgObject = new PdfImageXObject((PdfStream) imgStream.getIndirectReference().getRefersTo());
        PdfImageXObject imgObject = new PdfImageXObject(imgStream);
        pdfDoc.close();
        Image image = new Image(imgObject);
        image.setFixedPosition(0, 0);
        image.scaleToFit(pdfDoc.getPage(1).getPageSize().getWidth(), pdfDoc.getPage(1).getPageSize().getHeight());
        pdfDoc.close();

        // TODO Add all streams to document in order to render it well
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        doc.add(image);
        doc.close();

    }
}
