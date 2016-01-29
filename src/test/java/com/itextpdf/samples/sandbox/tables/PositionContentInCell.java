/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class PositionContentInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/position_content_in_cell.pdf";
    public static final String IMG = "./src/test/resources/sandbox/tables/info.png";

    public enum POSITION {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT}

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PositionContentInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        // 1. Create a Document which contains a table:
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Table table = new Table(2);
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();
        Cell cell4 = new Cell();
        // 2. Inside that table, make each cell with specific height:
        cell1.setHeight(50);
        cell2.setHeight(50);
        cell3.setHeight(50);
        cell4.setHeight(50);
        // 3. Each cell has the same background image
        // 4. Add text in front of the image at specific position
        cell1.setNextRenderer(new ImageAndPositionRenderer(cell1,
                new Image(ImageFactory.getImage(IMG)), "Top left", POSITION.TOP_LEFT));
        cell2.setNextRenderer(new ImageAndPositionRenderer(cell2,
                new Image(ImageFactory.getImage(IMG)), "Top right", POSITION.TOP_RIGHT));
        cell3.setNextRenderer(new ImageAndPositionRenderer(cell3,
                new Image(ImageFactory.getImage(IMG)), "Bottom left", POSITION.BOTTOM_LEFT));
        cell4.setNextRenderer(new ImageAndPositionRenderer(cell4,
                new Image(ImageFactory.getImage(IMG)), "Bottom right", POSITION.BOTTOM_RIGHT));
        // Wrap it all up!
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        doc.add(table);

        doc.close();
    }


    private class ImageAndPositionRenderer extends CellRenderer {
        private Image img;
        private String content;
        private POSITION position;

        public ImageAndPositionRenderer(Cell modelElement, Image img, String content, POSITION position) {
            super(modelElement);
            this.img = img;
            this.content = content;
            this.position = position;
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            img.scaleToFit(getOccupiedAreaBBox().getWidth(), getOccupiedAreaBBox().getHeight());

            drawContext.getCanvas().addXObject(img.getXObject(),
                    getOccupiedAreaBBox().getX() +
                            (getOccupiedAreaBBox().getWidth()
                                    - img.getImageWidth() * (float) img.getProperty(Property.HORIZONTAL_SCALING)) / 2,
                    getOccupiedAreaBBox().getY() +
                            (getOccupiedAreaBBox().getHeight()
                                    - img.getImageHeight() * (float) img.getProperty(Property.VERTICAL_SCALING)) / 2,
                    img.getImageWidth() * (float) img.getProperty(Property.HORIZONTAL_SCALING));
            drawContext.getCanvas().stroke();

            float x = 0;
            float y = 0;
            Property.TextAlignment alignment;
            // TODO content has not leading yet, we can't use y = ... - canvas.getGraphicsState().getLeading();
            switch (position) {
                case TOP_LEFT:
                    x = getOccupiedAreaBBox().getX() + 3;
                    y = getOccupiedAreaBBox().getY() + getOccupiedAreaBBox().getHeight() - 16;
                    alignment = Property.TextAlignment.LEFT;
                    break;
                case TOP_RIGHT:
                    x = getOccupiedAreaBBox().getX() + getOccupiedAreaBBox().getWidth() - 3;
                    y = getOccupiedAreaBBox().getY() + getOccupiedAreaBBox().getHeight() - 16;
                    alignment = Property.TextAlignment.RIGHT;
                    break;
                case BOTTOM_LEFT:
                    x = getOccupiedAreaBBox().getX() + 3;
                    y = getOccupiedAreaBBox().getY() + 3;
                    alignment = Property.TextAlignment.LEFT;
                    break;
                case BOTTOM_RIGHT:
                    x = getOccupiedAreaBBox().getX() + getOccupiedAreaBBox().getWidth() - 3;
                    y = getOccupiedAreaBBox().getY() + 3;
                    alignment = Property.TextAlignment.RIGHT;
                    break;
                default:
                    x = 0;
                    y = 0;
                    alignment = Property.TextAlignment.CENTER;
            }
            new Document(drawContext.getDocument()).showTextAligned(content, x, y, alignment);
        }
    }
}
