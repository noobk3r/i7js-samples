package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.IRenderer;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class NestedTables3 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/nested_tables3.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NestedTables3().manipulatePdf(DEST);
    }


    private class InnerTableRenderer extends TableRenderer {
        public InnerTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        protected InnerTableRenderer(Table modelElement) {
            super(modelElement);
        }

        @Override
        public void addChild(IRenderer renderer) {
            super.addChild(renderer);
        }

        @Override
        protected TableRenderer[] split(int row) {
            InnerTableRenderer splitRenderer = createSplitRenderer(
                    new Table.RowRange(rowRange.getStartRow(), rowRange.getStartRow() + row));
            splitRenderer.rows = rows.subList(0, row);
            InnerTableRenderer overflowRenderer = createOverflowRenderer(
                    new Table.RowRange(rowRange.getStartRow() + row,rowRange.getFinishRow()));
            overflowRenderer.rows = rows.subList(row, rows.size());
            splitRenderer.occupiedArea = occupiedArea;

            return new TableRenderer[] {splitRenderer, overflowRenderer};
        }

        @Override
        public void drawChildren(DrawContext drawContext) {
            super.drawChildren(drawContext);
            for (IRenderer renderer : childRenderers) {
                PdfCanvas canvas = drawContext.getCanvas();
                canvas.beginText();
                canvas.moveText(renderer.getOccupiedArea().getBBox().getLeft(),
                        renderer.getOccupiedArea().getBBox().getTop() - this.getPropertyAsFloat(Property.FONT_SIZE));
                canvas.setFontAndSize(this.getPropertyAsFont(Property.FONT),
                        this.getPropertyAsFloat(Property.FONT_SIZE));
                canvas.showText("This inner table header will always be repeated");
                canvas.endText();
                canvas.stroke();
            }
        }

        @Override
        public InnerTableRenderer getNextRenderer() {
            return new InnerTableRenderer((Table) modelElement);
        }
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Table table = new Table(2);
        table.setNextRenderer(new InnerTableRenderer(table, new Table.RowRange(0, 0)));
        Cell cell = new Cell(1,2).add(new Paragraph("This outer header is repeated on every page"));
        table.addHeaderCell(cell);
        Table inner1 = new Table(1);
        cell = new Cell();
        // TODO Implement setFixedHeight(float)
        //cell.setFixedHeight(20);
        cell.setHeight(20);
        inner1.addHeaderCell(cell);
        cell = new Cell().add(new Paragraph("This inner header won't be repeated on every page"));
        inner1.addHeaderCell(cell);
        for (int i = 0; i < 10; i++) {
            inner1.addCell(new Cell().add(new Paragraph("test")));
        }
        cell = new Cell().add(inner1);
        table.addCell(cell);
        Table inner2 = new Table(1);
        cell = new Cell();
        cell.setHeight(20);
        inner2.addHeaderCell(cell);
        cell = new Cell().add(new Paragraph("This inner may be repeated on every page"));
        inner2.addHeaderCell(cell);
        for (int i = 0; i < 35; i++) {
            inner2.addCell(new Cell().add(new Paragraph("test")));
        }
        cell = new Cell().add(inner2);
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
