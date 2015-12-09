/**
 * This example was written by Bruno Lowagie in answer to a question by a customer.
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.extgstate.PdfExtGState;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class TransparentWatermark2 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/pages.pdf";
    public static final String IMG = "./src/test/resources/sandbox/stamper/itext.png";
    public static final String DEST = "./target/test/resources/sandbox/stamper/transparent_watermark2.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TransparentWatermark2().manipulatePdf(DEST);
    }

    public static Rectangle getPageSizeWithRotation(PdfPage page) {
        PageSize rect = new PageSize(page.getPageSize());
        int rotation = page.getRotation();
        while (rotation > 0) {
            rect = rect.rotate();
            rotation -= 90;
        }
        return rect;
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        int n = pdfDoc.getNumOfPages();

        PdfFont font = new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI));
        Paragraph p = new Paragraph("My watermark (text)").setFont(font).setFontSize(30);
        // image watermark
        Image img = ImageFactory.getImage(IMG);
        //  Implement transformation matrix usage in order to scale image
        float w = img.getWidth();
        float h = img.getHeight();
        // transparency
        PdfExtGState gs1 = new PdfExtGState();
        gs1.setFillOpacity(0.5f);
        // properties
        PdfCanvas over;
        Rectangle pagesize;
        float x, y;
        // loop over every page
        for (int i = 1; i <= n; i++) {
            // TODO No "from box" getPageSize with rotation
            pagesize = getPageSizeWithRotation(pdfDoc.getPage(i));
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = new PdfCanvas(pdfDoc.getPage(i));
            over.saveState();
            over.setExtGState(gs1);
            if (i % 2 == 1) {
                doc.showTextAligned(p, x, y, i, Property.TextAlignment.CENTER, Property.VerticalAlignment.TOP, 0);
            } else {
                over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2), false);
            }
            over.restoreState();
        }
        pdfDoc.close();
    }
}
