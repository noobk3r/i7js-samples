/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.canvas.PdfPatternCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.PatternColor;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.colorspace.PdfPattern;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class TiledBackground extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/tiled_background.pdf";
    public static final String IMG1 = "./src/test/resources/sandbox/tables/ALxRF.png";
    public static final String IMG2 = "./src/test/resources/sandbox/tables/bulb.gif";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TiledBackground().manipulatePdf(DEST);
    }

    private class TiledImageBackgroundCellRenderer extends CellRenderer {
        protected Image img;

        public TiledImageBackgroundCellRenderer(Cell modelElement, Image img) {
            super(modelElement);
            this.img = img;
        }

        public void colorRectangle(PdfCanvas canvas, Color color, float x, float y, float width, float height) {
            canvas.saveState().setFillColor(color).rectangle(x, y, width, height).fillStroke().restoreState();
        }

        @Override
        public void draw(DrawContext drawContext) {
            PdfPattern.Tiling img_pattern = new PdfPattern.Tiling(img.getWidth(), img.getHeight(), img.getWidth(),
                    img.getHeight()).makeIndirect(drawContext.getDocument());
            new PdfPatternCanvas(img_pattern, drawContext.getDocument()).addImage(img, 0, 0, false);
            PdfCanvas canvas = drawContext.getCanvas();
            colorRectangle(canvas, new PatternColor(img_pattern), getOccupiedAreaBBox().getX(),
                    getOccupiedAreaBBox().getY(), getOccupiedAreaBBox().getWidth(), getOccupiedAreaBBox().getHeight());
            canvas.setFillColor(new PatternColor(img_pattern));
            canvas.rectangle(getOccupiedAreaBBox());
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
        Cell cell = new Cell();
        Image image = ImageFactory.getImage(IMG1);
        cell.setNextRenderer(new TiledImageBackgroundCellRenderer(cell, image));
        cell.setHeight(770);
        table.addCell(cell);
        cell = new Cell();
        image = ImageFactory.getImage(IMG2);
        cell.setNextRenderer(new TiledImageBackgroundCellRenderer(cell, image));
        cell.setHeight(770);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
