package com.itextpdf.samples.book.part3.chapter10;


import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.extgstate.PdfExtGState;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.pdf.xobject.PdfTransparencyGroup;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_10_22_TransparentOverlay extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part3/chapter10/Listing_10_22_TransparentOverlay.pdf";
    /** One of the resources. */
    public static final String RESOURCE
            = "./src/test/resources/book/part3/chapter10/bruno_ingeborg.jpg";

    public static void main(String args[]) throws IOException {
        new Listing_10_22_TransparentOverlay().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(850, 600));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        Image img = ImageFactory.getImage(RESOURCE);
        com.itextpdf.model.element.Image imgModel = new com.itextpdf.model.element.Image(img);
        float w = imgModel.getImageScaledWidth();
        float h = imgModel.getImageScaledHeight();
        canvas.ellipse(1, 1, 848, 598);
        canvas.clip();
        canvas.newPath();
        canvas.addImage(img, w, 0, 0, h, 0, -600);

        PdfFormXObject xObject2 = new PdfFormXObject(new Rectangle(850, 600));
        PdfCanvas xObject2Canvas = new PdfCanvas(xObject2, pdfDoc);
        PdfTransparencyGroup transGroup = new PdfTransparencyGroup();
        transGroup.put( PdfName.CS, PdfName.DeviceGray);
        transGroup.setIsolated(true);
        transGroup.setKnockout(false);
        xObject2.setGroup(transGroup);

        // Add transparent ellipses to the template
        int gradationStep = 30;
        float[] gradationRatioList = new float[gradationStep];
        for(int i = 0; i < gradationStep; i++) {
            gradationRatioList[i] = 1 - (float)Math.sin(Math.toRadians(90.0f / gradationStep * (i + 1)));
        }
        for(int i = 1; i < gradationStep + 1; i++) {
            xObject2Canvas.setLineWidth(5 * (gradationStep + 1 - i));
            xObject2Canvas.setStrokeColorGray(gradationRatioList[gradationStep - i]);
            xObject2Canvas.ellipse(0, 0, 850, 600);
            xObject2Canvas.stroke();
        }

        // Create an image mask for the direct content
        PdfDictionary maskDict = new PdfDictionary();
        maskDict.put(PdfName.Type, PdfName.Mask);
        maskDict.put(PdfName.S, PdfName.Luminosity);
        maskDict.put(new PdfName("G"), xObject2.getPdfObject());
        PdfExtGState gState = new PdfExtGState();
        gState.put(PdfName.SMask, maskDict);
        canvas.setExtGState(gState);

        canvas.addXObject(xObject2, 0, 0);

        doc.close();
    }
}
