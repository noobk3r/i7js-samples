package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
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


    private class OverlappingImageTableRenderer extends TableRenderer {
        private Image image;

        public OverlappingImageTableRenderer(Table modelElement, Table.RowRange rowRange, Image img) {
            super(modelElement, rowRange);
            this.image = img;
        }

        @Override
        public void drawChildren(PdfDocument document, PdfCanvas canvas) {
            super.drawChildren(document, canvas);
            float x = Math.max(this.getOccupiedAreaBBox().getX() +
                    this.getOccupiedAreaBBox().getWidth() / 3 - image.getWidth(), 0);
            float y = Math.max(this.getOccupiedAreaBBox().getY() +
                    this.getOccupiedAreaBBox().getHeight() / 3 - image.getHeight(), 0);
            canvas.addImage(image, x, y, false);
        }

        @Override
        protected TableRenderer createSplitRenderer(Table.RowRange rowRange) {
            OverlappingImageTableRenderer splitRenderer =
                    new OverlappingImageTableRenderer((Table) modelElement, rowRange, image);
            splitRenderer.parent = parent;
            splitRenderer.modelElement = modelElement;
            // TODO childRenderers will be populated twice during the relayout.
            // We should probably clean them before #layout().
            splitRenderer.childRenderers = childRenderers;
            splitRenderer.addAllProperties(getOwnProperties());
            splitRenderer.headerRenderer = headerRenderer;
            splitRenderer.footerRenderer = footerRenderer;
            return splitRenderer;
        }

        @Override
        protected TableRenderer createOverflowRenderer(Table.RowRange rowRange) {
            OverlappingImageTableRenderer overflowRenderer =
                    new OverlappingImageTableRenderer((Table) modelElement, rowRange, image);
            overflowRenderer.parent = parent;
            overflowRenderer.modelElement = modelElement;
            overflowRenderer.addAllProperties(getOwnProperties());
            overflowRenderer.isOriginalNonSplitRenderer = false;
            return overflowRenderer;
        }
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
}
