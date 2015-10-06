/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26983703/itext-how-to-stamp-image-on-existing-pdf-and-create-an-anchor
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddImageLink extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/primes.pdf";
    public static final String IMG = "./src/test/resources/sandbox/annotations/info.png";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_image_link.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddImageLink().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Image img = ImageFactory.getImage(IMG);
        float x = 10;
        float y = 650;
        float w = img.getWidth();
        float h = img.getHeight();
        new PdfCanvas(pdfDoc.getPage(1).newContentStreamAfter(), pdfDoc.getPage(1).getResources(), pdfDoc)
                .addImage(img, x, y, false);
        Rectangle linkLocation = new Rectangle(x, y, x + w, y + h);

        PdfArray array = new PdfArray();
        array.add(doc.getPdfDocument().getPage(pdfDoc.getNumOfPages()).getPdfObject());
        array.add(PdfName.Fit);
        PdfDestination destination = PdfDestination.makeDestination(array);
        PdfAnnotation annotation = new PdfLinkAnnotation(pdfDoc, linkLocation)
                .setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT)
                .setAction(PdfAction.createGoTo(pdfDoc, destination));
        pdfDoc.getPage(1).addAnnotation(annotation);

        pdfDoc.close();
    }

}
