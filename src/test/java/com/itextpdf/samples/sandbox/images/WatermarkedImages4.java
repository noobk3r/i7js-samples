/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/29561417/draw-lines-on-image-in-pdf-using-itextsharp
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class WatermarkedImages4 extends GenericTest {
    public static final String IMAGE1 = "./src/test/resources/sandbox/images/bruno.jpg";
    public static final String IMAGE2 = "./src/test/resources/sandbox/images/dog.bmp";
    public static final String IMAGE3 = "./src/test/resources/sandbox/images/fox.bmp";
    public static final String IMAGE4 = "./src/test/resources/sandbox/images/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/watermarked_images4.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new WatermarkedImages4().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        doc.add(getWatermarkedImage(pdfDoc, new Image(ImageFactory.getImage(IMAGE1))));
        doc.add(getWatermarkedImage(pdfDoc, new Image(ImageFactory.getImage(IMAGE2))));
        doc.add(getWatermarkedImage(pdfDoc, new Image(ImageFactory.getImage(IMAGE3))));
        Image image = new Image(ImageFactory.getImage(IMAGE4));
        image.scaleToFit(400, 700);
        doc.add(getWatermarkedImage(pdfDoc, image));
        doc.close();
    }

    public Image getWatermarkedImage(PdfDocument pdfDocument, Image img) {
        float width = img.getImageScaledWidth();
        float height = img.getImageScaledHeight();
        PdfFormXObject template = new PdfFormXObject(new Rectangle(width, height));
        new Canvas(template, pdfDocument).add(img);
        new PdfCanvas(template, pdfDocument).
            saveState().
            setStrokeColor(Color.GREEN).
            setLineWidth(3).
            moveTo(width * .25f, height * .25f).
            lineTo(width * .75f, height * .75f).
            moveTo(width * .25f, height * .75f).
            lineTo(width * .25f, height * .25f).
            stroke().
                setStrokeColor(Color.WHITE).
            ellipse(0, 0, width, height).
            stroke().
            restoreState();
        return new Image(template);
    }
}
