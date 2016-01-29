/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/


/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26814958/pdf-vertical-postion-method-gives-the-next-page-position-instead-of-current-page
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class WatermarkedImages2 extends GenericTest {
    public static final String IMAGE1 = "./src/test/resources/sandbox/images/bruno.jpg";
    public static final String IMAGE2 = "./src/test/resources/sandbox/images/dog.bmp";
    public static final String IMAGE3 = "./src/test/resources/sandbox/images/fox.bmp";
    public static final String IMAGE4 = "./src/test/resources/sandbox/images/bruno_ingeborg.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/watermarked_images2.pdf";

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

        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE1)).setAutoScaleWidth(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Bruno"));
        table.addCell(cell);

        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE2)).setAutoScaleWidth(true));
        cell.setNextRenderer(new WatermarkedCellRenderer(cell, "Dog"));
        table.addCell(cell);

        cell = new Cell().add(new Image(ImageFactory.getImage(IMAGE3)).setAutoScaleWidth(true));
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

        public WatermarkedCellRenderer(Cell modelElement, String content) {
            super(modelElement);
            this.content = content;
        }

        @Override
        public CellRenderer getNextRenderer() {
            return new WatermarkedCellRenderer((Cell) modelElement, content);
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            new Document(drawContext.getDocument()).showTextAligned(new Paragraph(content).setFontColor(DeviceRgb.WHITE),
                (getOccupiedAreaBBox().getLeft() + getOccupiedAreaBBox().getRight()) / 2,
                (getOccupiedAreaBBox().getTop() + getOccupiedAreaBBox().getBottom()) / 2,
                occupiedArea.getPageNumber(),
                Property.TextAlignment.CENTER,
                Property.VerticalAlignment.MIDDLE,
                (float)Math.PI / 180 * 30);
        }
    }
}
