package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutContext;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.IRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

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
        }

        @Override
        public LayoutResult layout(LayoutContext layoutContext) {
            PdfFont bf = getPropertyAsFont(Property.FONT);
            float availableWidth = layoutContext.getArea().getBBox().getWidth();
            int contentLength = content.length();
            int leftChar = 0;
            int rightChar = contentLength - 1;
            availableWidth -= bf.getWidthPoint("...", 12);
            while (leftChar < contentLength && rightChar != leftChar) {
                availableWidth -= bf.getWidthPoint(content.charAt(leftChar), 12);
                if (availableWidth > 0)
                    leftChar++;
                else
                    break;
                availableWidth -= bf.getWidthPoint(content.charAt(rightChar), 12);
                if (availableWidth > 0)
                    rightChar--;
                else
                    break;
            }
            String newContent = content.substring(0, leftChar) + "..." + content.substring(rightChar);
            Paragraph p = new Paragraph(newContent);

            IRenderer pr = p.createRendererSubTree().setParent(this);
            this.childRenderers.add(pr);
            return super.layout(layoutContext);
        }
    }
}



