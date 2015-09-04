package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.IRenderer;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Ignore
@Category(SampleTest.class)
public class TruncateTextInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/truncate_text_in_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TruncateTextInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(5);
        //TODO Implement setWidthPercentage(float)
        //table.setWidthPercentage(100);
        Cell cell;
        for (int r = 'A'; r <= 'Z'; r++) {
            for (int c = 1; c <= 5; c++) {
                cell = new Cell();
                if (r == 'D' && c == 2) {
                    cell.setNextRenderer(new FitCellRenderer(cell,
                            "D2 is a cell with more content than we can fit into the cell."));
                } else {
                    cell.add(new Paragraph(String.valueOf((char) r) + String.valueOf(c)));
                }
                table.addCell(cell);
            }
        }
        doc.add(table);

        doc.close();
    }

    static private class FitCellRenderer extends CellRenderer {
        private String content;

        public FitCellRenderer(Cell modelElement, String content) throws IOException {
            super(modelElement);
            this.content = content;
//            this.modelElement = modelElement;
//            setProperty(Property.ROWSPAN, modelElement.getRowspan());
//            setProperty(Property.COLSPAN, modelElement.getColspan());
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            Paragraph p = new Paragraph(content);
            p.setFontSize(12);
            IRenderer pr = p.createRendererSubTree();
            pr.setParent(this);
            // TODO Implement analog of ColumnText to find real width value
            // the next line doesn't woke correctly eben though with heigth it's efficient
            // LayoutResult textArea = pr.layout(new LayoutContext(new LayoutArea(0,
            // new Rectangle(0, 0, 10000000, getOccupiedAreaBBox().getHeight()))));
            // TODO Implement PdfTemplate usage
//                PdfTemplate tmp = canvas.createTemplate(position.getWidth(), position.getHeight());
//                ct = new ColumnText(tmp);
//                ct.setSimpleColumn(0, offset, position.getWidth(), offset + spaceneeded);
//                ct.addElement(content);
//                ct.go();
//                canvas.addTemplate(tmp, position.getLeft(), position.getBottom());
            canvas.beginText();
            canvas.moveText(getOccupiedAreaBBox().getX() + 3, getOccupiedAreaBBox().getY() + 3);
            canvas.setFontAndSize(this.getPropertyAsFont(Property.FONT), this.getPropertyAsFloat(Property.FONT_SIZE));
            canvas.showText("TODO!!!");
            canvas.endText();
            canvas.stroke();
        }
    }
}



