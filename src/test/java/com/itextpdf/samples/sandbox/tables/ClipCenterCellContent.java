package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutContext;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.IRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ClipCenterCellContent extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/clip_center_cell_content.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ClipCenterCellContent().manipulatePdf(DEST);
    }

    private class ClipCenterCellContentCellRenderer extends CellRenderer {
        private Paragraph content;

        public ClipCenterCellContentCellRenderer(Cell modelElement, Paragraph content) {
            super(modelElement);
            this.content = content;
        }

        @Override
        public void draw(DrawContext drawContext) {
            IRenderer pr = content.createRendererSubTree().setParent(this);
            LayoutResult textArea = pr.layout(new LayoutContext(
                    new LayoutArea(0, new Rectangle(getOccupiedAreaBBox().getWidth(), 1000))));

            float spaceneeded = textArea.getOccupiedArea().getBBox().getHeight();
            System.out.println(String.format("The content requires %s pt whereas the height is %s pt.",
                    spaceneeded, getOccupiedAreaBBox().getHeight()));
            float offset = (getOccupiedAreaBBox().getHeight() - textArea.getOccupiedArea().getBBox().getHeight()) / 2;
            System.out.println(String.format("The difference is %s pt; we'll need an offset of %s pt.",
                    -2f * offset, offset));

            PdfFormXObject xObject = new PdfFormXObject(new Rectangle(getOccupiedArea().getBBox().getWidth(), getOccupiedArea().getBBox().getHeight()));
            Canvas layoutCanvas = new Canvas(new PdfCanvas(xObject, drawContext.getDocument()), drawContext.getDocument(),
                    new Rectangle(0, offset, getOccupiedArea().getBBox().getWidth(), spaceneeded));
            layoutCanvas.add(content);

            drawContext.getCanvas().addXObject(xObject, occupiedArea.getBBox().getLeft(), occupiedArea.getBBox().getBottom());
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4);

        Table table = new Table(5);
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 5; c++) {
                cell = new Cell().setHeight(30);
                if (r == 'D' && c == 2) {
                    cell.setNextRenderer(new ClipCenterCellContentCellRenderer(cell,
                            new Paragraph("D2 is a cell with more content than we can fit into the cell.")));
                } else {
                    cell.add(new Paragraph(String.valueOf((char) r) + String.valueOf(c)));
                }
                table.addCell(cell);
            }
        }
        doc.add(table);

        doc.close();
    }
}
