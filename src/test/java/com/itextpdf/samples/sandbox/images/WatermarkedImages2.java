/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26814958/pdf-vertical-postion-method-gives-the-next-page-position-instead-of-current-page
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
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

@Ignore
@Category(SampleTest.class)
public class WatermarkedImages2 extends GenericTest {
    public static final String IMAGE1 = "./src/test/resources/sandbox/images/bruno.jpg";
    public static final String IMAGE2 = "./src/test/resources/sandbox/images/dog.bmp";
    public static final String IMAGE3 = "./src/test/resources/sandbox/images/fox.bmp";
    public static final String IMAGE4 = "./src/test/resources/sandbox/images/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/watermarked_images2.pdf";

    //public static final Font FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, GrayColor.GRAYWHITE);
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

        Table table = new Table(1);
        Cell cell;
        // TODO we don't know what rectangle to fit because cell will have sizes only after adding to a document
        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE1)).setAutoScale(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Bruno"));
        table.addCell(cell);
        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE2)).setAutoScale(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Dog"));
        table.addCell(cell);
        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE3)).setAutoScale(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Fox"));
        table.addCell(cell);
        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE4)).setAutoScale(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Bruno and Ingeborg"));
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }


    private class WatermarkedCellRenderer extends CellRenderer {
        private String content;

        public WatermarkedCellRenderer(Cell modelElement, String content) throws IOException {
            super(modelElement);
            this.content = content;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
//            canvas.beginText();
//            canvas.moveText((getOccupiedAreaBBox().getLeft()+getOccupiedAreaBBox().getRight()) / 2,
//                    (getOccupiedAreaBBox().getTop()+getOccupiedAreaBBox().getBottom()) / 2);
            PdfFont font = null;
            try {
                font = new PdfType1Font(document, (Type1Font) FontFactory.createFont(FontConstants.HELVETICA));
            } catch (IOException e) {
                // we do not expect this to happen :)
            }
            // TODO Implement usage of fontstyles such as Font.NORMAL
//            canvas.setStrokeColor(Color.GRAY);
            // TODO Implement showTextAligned to show text over content
            new Document(document).showTextAligned(
                    new Paragraph(content).setFont(font).setFontSize(12).setFontColor(Color.LIGHT_GRAY),
                    (getOccupiedAreaBBox().getLeft() + getOccupiedAreaBBox().getRight()) / 2,
                    (getOccupiedAreaBBox().getTop() + getOccupiedAreaBBox().getBottom()) / 2,
                    document.getNumOfPages(),
                    Property.TextAlignment.CENTER,
                    null,
                    30 * 0.0174532925f);
//            canvas.showText(content);
//            canvas.endText();
//            canvas.stroke();
        }
    }
}
