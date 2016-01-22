package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class RoundedCorners extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/rounded_corners.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RoundedCorners().manipulatePdf(DEST);
    }


    private class RoundedCornersCellRenderer extends CellRenderer {
        public RoundedCornersCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(DrawContext drawContext) {
            float llx = getOccupiedAreaBBox().getX() + 2;
            float lly = getOccupiedAreaBBox().getY() + 2;
            float urx = getOccupiedAreaBBox().getX() + getOccupiedAreaBBox().getWidth() - 2;
            float ury = getOccupiedAreaBBox().getY() + getOccupiedAreaBBox().getHeight() - 2;
            float r = 4;
            float b = 0.4477f;
            PdfCanvas canvas = drawContext.getCanvas();
            canvas.moveTo(llx, lly);
            canvas.lineTo(urx, lly);
            canvas.lineTo(urx, ury - r);
            canvas.curveTo(urx, ury - r * b, urx - r * b, ury, urx - r, ury);
            canvas.lineTo(llx + r, ury);
            canvas.curveTo(llx + r * b, ury, llx, ury - r * b, llx, ury - r);
            canvas.lineTo(llx, lly);
            canvas.stroke();
            super.draw(drawContext);
        }
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(3);
        Cell cell = getCell("These cells have rounded borders at the top.");
        table.addCell(cell);
        cell = getCell("These cells aren't rounded at the bottom.");
        table.addCell(cell);
        cell = getCell("A custom cell event was used to achieve this.");
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }

    public Cell getCell(String content) {
        Cell cell = new Cell().add(new Paragraph(content));
        cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
        cell.setPadding(5);
        cell.setBorder(null);
        return cell;
    }
}
