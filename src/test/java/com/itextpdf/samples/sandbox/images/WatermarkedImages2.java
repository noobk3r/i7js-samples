
/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26814958/pdf-vertical-postion-method-gives-the-next-page-position-instead-of-current-page
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("DEVSIX-366")
@Category(SampleTest.class)
public class WatermarkedImages2 extends GenericTest {
    public static final String IMAGE1 = "./src/test/resources/sandbox/images/bruno.jpg";
    public static final String IMAGE2 = "./src/test/resources/sandbox/images/dog.bmp";
    public static final String IMAGE3 = "./src/test/resources/sandbox/images/fox.bmp";
    public static final String IMAGE4 = "./src/test/resources/sandbox/images/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/watermarked_images2.pdf";
    protected static PdfFont font = null;

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new WatermarkedImages2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        font =  new PdfType1Font(pdfDoc, (Type1Font)FontFactory.createFont(FontConstants.HELVETICA));

        Table table = new Table(1);
        Cell cell;

        com.itextpdf.basics.image.Image image1 = ImageFactory.getImage(IMAGE1);
        cell = new Cell().add(new Image(image1).setAutoScaleWidth(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Bruno", 1));
        table.addCell(cell);

        com.itextpdf.basics.image.Image image2 = ImageFactory.getImage(IMAGE2);
        cell = new Cell().add(new Image(image2).setAutoScaleWidth(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Dog", 1));
        table.addCell(cell);


        com.itextpdf.basics.image.Image image3 = ImageFactory.getImage(IMAGE3);
        cell = new Cell().add(new Image(image3).setAutoScaleWidth(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Fox", 2));
        table.addCell(cell);

        com.itextpdf.basics.image.Image image4 = ImageFactory.getImage(IMAGE4);
        cell = new Cell().add(new Image(image4).scaleToFit(400, 700));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Bruno and Ingeborg", 3));
        table.addCell(cell);

        doc.add(table);
        doc.close();
    }


    private class WatermarkedCellRenderer extends CellRenderer {
        private String content;
        private int pageNum;
        public WatermarkedCellRenderer(Cell modelElement, String content,int pageNum) throws IOException {
            super(modelElement);
            this.content = content;
            this.pageNum = pageNum;
        }



        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            new Document(document).showTextAligned(
                    new Paragraph(content).setFont(font).setFontSize(12).setFontColor(Color.LIGHT_GRAY),
                    (getOccupiedAreaBBox().getLeft() + getOccupiedAreaBBox().getRight()) / 2,
                    (getOccupiedAreaBBox().getTop() + getOccupiedAreaBBox().getBottom()) / 2,
                    pageNum,
                    Property.TextAlignment.CENTER,
                    Property.VerticalAlignment.MIDDLE,
                    0.5f);

        }

    }



}
