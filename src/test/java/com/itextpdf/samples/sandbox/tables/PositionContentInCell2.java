package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
public class PositionContentInCell2 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/position_content_in_cell2.pdf";
    public static final String IMG = "./src/test/resources/sandbox/tables/info.png";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PositionContentInCell2().manipulatePdf(DEST);
    }


    private class ImageAndPositionRenderer extends CellRenderer {

        private Image img;
        private String content;
        private Property.HorizontalAlignment alignment;
        private float wPct;
        private float hPct;

        public ImageAndPositionRenderer(Cell modelElement, float wPct, float hPct,
                                        Image img, String content, Property.HorizontalAlignment alignment) {
            super(modelElement);
            this.img = img;
            this.content = content;
            this.alignment = alignment;
            this.wPct = wPct;
            this.hPct = hPct;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);

            canvas.addXObject(img.getXObject(), getOccupiedAreaBBox());
            canvas.stroke();
            canvas.beginText();
            canvas.setFontAndSize(this.getPropertyAsFont(Property.FONT), this.getPropertyAsFloat(Property.FONT_SIZE));

            float x = getOccupiedAreaBBox().getX() + wPct * getOccupiedAreaBBox().getWidth();
            float y = getOccupiedAreaBBox().getY() + hPct * (getOccupiedAreaBBox().getHeight());
            canvas.moveText(x, y);
            // TODO Implement showTextAligned
            //ColumnText.showTextAligned(canvas, alignment, content, x, y, 0);
            canvas.showText(content);
            canvas.endText();
            canvas.stroke();
        }
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Table table = new Table(2);
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();
        Cell cell4 = new Cell();
        Cell cell5 = new Cell();
        Cell cell6 = new Cell();
        Cell cell7 = new Cell();
        Cell cell8 = new Cell();
        // 2. Inside that table, make each cell with specific height:
        cell1.setHeight(50);
        cell2.setHeight(50);
        cell3.setHeight(50);
        cell4.setHeight(50);
        cell5.setHeight(50);
        cell6.setHeight(50);
        cell7.setHeight(50);
        cell8.setHeight(50);
        // 3. Each cell has the same background image
        // 4. Add text in front of the image at specific position
        cell1.setNextRenderer(new ImageAndPositionRenderer(cell1, 0, 1,
                new Image(ImageFactory.getImage(IMG)), "Top left", Property.HorizontalAlignment.LEFT));
        cell2.setNextRenderer(new ImageAndPositionRenderer(cell2, 1, 1,
                new Image(ImageFactory.getImage(IMG)), "Top right", Property.HorizontalAlignment.RIGHT));
        cell3.setNextRenderer(new ImageAndPositionRenderer(cell3, 0.5f, 1,
                new Image(ImageFactory.getImage(IMG)), "Top center", Property.HorizontalAlignment.CENTER));
        cell4.setNextRenderer(new ImageAndPositionRenderer(cell4, 0.5f, 0,
                new Image(ImageFactory.getImage(IMG)), "Bottom center", Property.HorizontalAlignment.CENTER));
        cell5.setNextRenderer(new ImageAndPositionRenderer(cell5, 0.5f, 0.5f,
                new Image(ImageFactory.getImage(IMG)), "Middle center", Property.HorizontalAlignment.CENTER));
        cell6.setNextRenderer(new ImageAndPositionRenderer(cell6, 0.5f, 0.5f,
                new Image(ImageFactory.getImage(IMG)), "Middle center", Property.HorizontalAlignment.CENTER));
        cell7.setNextRenderer(new ImageAndPositionRenderer(cell7, 0, 0,
                new Image(ImageFactory.getImage(IMG)), "Bottom left", Property.HorizontalAlignment.LEFT));
        cell8.setNextRenderer(new ImageAndPositionRenderer(cell8, 1, 0,
                new Image(ImageFactory.getImage(IMG)), "Bottom right", Property.HorizontalAlignment.RIGHT));
        // Wrap it all up!
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        doc.add(table);

        doc.close();
    }
}
