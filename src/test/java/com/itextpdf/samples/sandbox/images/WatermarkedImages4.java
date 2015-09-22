/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/29561417/draw-lines-on-image-in-pdf-using-itextsharp
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
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
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO Implement Document.add on special content (for instance, before-content)
        // PdfContentByte cb = writer.getDirectContentUnder();
        doc.add(getWatermarkedImage(canvas, new Image(ImageFactory.getImage(IMAGE1))));
        doc.add(getWatermarkedImage(canvas, new Image(ImageFactory.getImage(IMAGE2))));
        doc.add(getWatermarkedImage(canvas, new Image(ImageFactory.getImage(IMAGE3))));
        Image image = new Image(ImageFactory.getImage(IMAGE4));
        image.scaleToFit(400, 400);
        doc.add(getWatermarkedImage(canvas, image));
        doc.close();
    }

    public Image getWatermarkedImage(PdfCanvas cb, Image img) {
        float width = img.getImageWidth();
        float height = img.getImageHeight();
        // TODO Implement PdfTemplate
        // PdfTemplate template = cb.createTemplate(width, height);
//        template.addImage(img, width, 0, 0, height, 0, 0);
//        template.saveState();
//        template.setColorStroke(BaseColor.GREEN);
//        template.setLineWidth(3);
//        template.moveTo(width * .25f, height * .25f);
//        template.lineTo(width * .75f, height * .75f);
//        template.moveTo(width * .25f, height * .75f);
//        template.lineTo(width * .25f, height * .25f);
//        template.stroke();
//        template.setColorStroke(BaseColor.WHITE);
//        template.ellipse(0, 0, width, height);
//        template.stroke();
//        template.restoreState();
//        return Image.getInstance(template);
        return null;
    }
}
