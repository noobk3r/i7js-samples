/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddOverlappingImage extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/add_overlapping_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddOverlappingImage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(5);
        table.setNextRenderer(new OverlappingImageTableRenderer(table, new Table.RowRange(0, 25),
                ImageFactory.getImage("./src/test/resources/sandbox/tables/hero.jpg")));
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 5; c++) {
                cell = new Cell();
                cell.add(new Paragraph(String.valueOf((char) r) + String.valueOf(c)));
                table.addCell(cell);
            }
        }
        doc.add(table);

        doc.close();
    }


    private class OverlappingImageTableRenderer extends TableRenderer {
        private Image image;

        public OverlappingImageTableRenderer(Table modelElement, Table.RowRange rowRange, Image img) {
            super(modelElement, rowRange);
            this.image = img;
        }

        public OverlappingImageTableRenderer(Table modelElement, Image img) {
            super(modelElement);
            this.image = img;
        }

        @Override
        public void drawChildren(DrawContext drawContext) {
            super.drawChildren(drawContext);
            float x = Math.max(this.getOccupiedAreaBBox().getX() +
                    this.getOccupiedAreaBBox().getWidth() / 3 - image.getWidth(), 0);
            float y = Math.max(this.getOccupiedAreaBBox().getY() +
                    this.getOccupiedAreaBBox().getHeight() / 3 - image.getHeight(), 0);
            drawContext.getCanvas().addImage(image, x, y, false);
        }

        @Override
        public OverlappingImageTableRenderer getNextRenderer() {
            return new OverlappingImageTableRenderer((Table) modelElement, image);
        }
    }
}
