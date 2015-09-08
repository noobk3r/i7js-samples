package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutContext;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.IRenderer;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
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
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            content.setFontSize(12);
            IRenderer pr = content.createRendererSubTree();
            pr.setParent(this);
            LayoutResult textArea = pr.layout(new LayoutContext(
                    new LayoutArea(0, new Rectangle(0, 0, getOccupiedAreaBBox().getWidth(), 10000))));

            System.out.println(String.format("The content requires %s pt whereas the height is %s pt.",
                    textArea.getOccupiedArea().getBBox().getHeight(), getOccupiedAreaBBox().getHeight()));
            float offset = (getOccupiedAreaBBox().getHeight() - textArea.getOccupiedArea().getBBox().getHeight()) / 2;
            System.out.println(String.format("The difference is %s pt; we'll need an offset of %s pt.",
                    -2f * offset, offset));
            // TODO Implement PdfTemplate usage in such situation
//                PdfTemplate tmp = canvas.createTemplate(position.getWidth(), position.getHeight());
//                ct = new ColumnText(tmp);
//                ct.setSimpleColumn(0, offset, position.getWidth(), offset + spaceneeded);
//                ct.addElement(content);
//                ct.go();
//                canvas.addTemplate(tmp, position.getLeft(), position.getBottom());
            canvas.beginText();
            canvas.moveText(getOccupiedAreaBBox().getX() + 3, getOccupiedAreaBBox().getY() + 3);
            canvas.setFontAndSize(this.getPropertyAsFont(Property.FONT), this.getPropertyAsFloat(Property.FONT_SIZE));
            canvas.showText("Implement PdfTemplate!!!");
            canvas.endText();
            canvas.stroke();
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Table table = new Table(5);
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 5; c++) {
                cell = new Cell();
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
