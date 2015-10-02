/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26814958/pdf-vertical-postion-method-gives-the-next-page-position-instead-of-current-page
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

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class WatermarkedImages1 extends GenericTest {
    public static final String IMAGE1 = "./src/test/resources/sandbox/images/bruno.jpg";
    public static final String IMAGE2 = "./src/test/resources/sandbox/images/dog.bmp";
    public static final String IMAGE3 = "./src/test/resources/sandbox/images/fox.bmp";
    public static final String IMAGE4 = "./src/test/resources/sandbox/images/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/watermarked_images1.pdf";

    //public static final Font FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, GrayColor.GRAYWHITE);
    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new WatermarkedImages1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO Implement Document.add on special content (for instance, before-content)
        doc.add(getWatermarkedImage(doc, new Image(ImageFactory.getImage(IMAGE1)), "Bruno"));
        doc.add(getWatermarkedImage(doc, new Image(ImageFactory.getImage(IMAGE2)), "Dog"));
        doc.add(getWatermarkedImage(doc, new Image(ImageFactory.getImage(IMAGE3)), "Fox"));
        Image image = new Image(ImageFactory.getImage(IMAGE4));
        image.scaleToFit(400, 700);
        doc.add(getWatermarkedImage(doc, image, "Bruno and Ingeborg"));
        doc.close();
    }

    public Image getWatermarkedImage(Document doc, Image img, String watermark) {
        float width = img.getImageWidth();
        float height = img.getImageHeight();
        /* This is how we can show aligned text*/
        //doc.showTextAligned(watermark, width / 2, height / 2, Property.HorizontalAlignment.CENTER, 30*0.0174532925f);
        // TODO Implement PdfTemplate
        // PdfTemplate template = cb.createTemplate(width, height);
        // template.addImage(img, width, 0, 0, height, 0, 0);
        // ColumnText.showTextAligned(template, Element.ALIGN_CENTER,
        //        new Phrase(watermark, FONT), width / 2, height / 2, 30);
        return null;
    }
}
