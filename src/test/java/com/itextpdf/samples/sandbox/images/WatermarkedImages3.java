/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/28515474/how-to-add-text-on-the-last-page-through-pdfcontentbyte
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class WatermarkedImages3 extends GenericTest {
    public static final String IMAGE1 = "./src/test/resources/sandbox/images/bruno.jpg";
    public static final String IMAGE2 = "./src/test/resources/sandbox/images/dog.bmp";
    public static final String IMAGE3 = "./src/test/resources/sandbox/images/fox.bmp";
    public static final String IMAGE4 = "./src/test/resources/sandbox/images/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/watermarked_images3.pdf";

    //public static final Font FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, GrayColor.GRAYWHITE);
    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new WatermarkedImages3().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Table table = new Table(1);
        for (int i = 0; i < 50; i++) {
            table.addCell(new Cell().add(new Paragraph("rahlrokks doesn't listen to what people tell him")));
        }
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO Implement Document.add on special content (for instance, before-content)
        // PdfContentByte cb = writer.getDirectContentUnder();
        table.addCell(new Cell().add(getWatermarkedImage(canvas, new Image(ImageFactory.getImage(IMAGE1)), "Bruno")));
        doc.add(table);
        doc.showTextAligned(new Paragraph("Bruno knows best"), 260, 400, pdfDoc.getNumOfPages(),
                Property.HorizontalAlignment.CENTER, null, 45 * 0.0174532925f);
        //ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("Bruno knows best"), 260, 400, 45);
        doc.close();
    }

    public Image getWatermarkedImage(PdfCanvas cb, Image img, String watermark) {
        float width = img.getImageWidth();
        float height = img.getImageHeight();
        // TODO Implement PdfTemplate
        // PdfTemplate template = cb.createTemplate(width, height);
        // template.addImage(img, width, 0, 0, height, 0, 0);
        // ColumnText.showTextAligned(template, Element.ALIGN_CENTER,
        //        new Phrase(watermark, FONT), width / 2, height / 2, 30);;
        return null;
    }
}
