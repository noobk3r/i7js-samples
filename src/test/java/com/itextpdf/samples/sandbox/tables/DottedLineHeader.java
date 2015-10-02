package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class DottedLineHeader extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/dotted_line_header.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DottedLineHeader().manipulatePdf(DEST);
    }

    private class DottedHeaderTableRenderer extends TableRenderer {
        public DottedHeaderTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        @Override
        public void drawChildren(PdfDocument document, PdfCanvas canvas) {
            super.drawChildren(document, canvas);
            canvas.setLineDash(3f, 3f);
            Rectangle headersArea = headerRenderer.getOccupiedArea().getBBox();
            canvas.moveTo(headersArea.getLeft(),
                    headersArea.getTop());
            canvas.lineTo(headersArea.getRight(),
                    headersArea.getTop());

            canvas.moveTo(headersArea.getLeft(),
                    headersArea.getBottom());
            canvas.lineTo(headersArea.getRight(),
                    headersArea.getBottom());
            canvas.stroke();
        }
    }

    private class DottedHeaderCellRenderer extends CellRenderer {
        public DottedHeaderCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            canvas.setLineDash(3f, 3f);
            canvas.moveTo(this.getOccupiedArea().getBBox().getLeft(), this.getOccupiedArea().getBBox().getBottom());
            canvas.lineTo(this.getOccupiedArea().getBBox().getRight(),
                    this.getOccupiedArea().getBBox().getBottom());
            canvas.moveTo(this.getOccupiedArea().getBBox().getLeft(),
                    this.getOccupiedArea().getBBox().getTop());
            canvas.lineTo(this.getOccupiedArea().getBBox().getRight(),
                    this.getOccupiedArea().getBBox().getTop());
            canvas.stroke();
        }
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(3);
        table.setNextRenderer(new DottedHeaderTableRenderer(table, new Table.RowRange(0, 1)));
        // TODO Implement setting-for-all-cells-specific-border method
        //table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.addHeaderCell(new Cell().add(new Paragraph("A1")).setBorder(null));
        table.addHeaderCell(new Cell().add(new Paragraph("A2")).setBorder(null));
        table.addHeaderCell(new Cell().add(new Paragraph("A3")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B3")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C3")).setBorder(null));
        doc.add(table);
        doc.add(new Paragraph("Cell event"));
        table = new Table(3);
        // TODO Implement setting-for-all-cells-specific-border method
        //table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        // TODO Implement default renderer for all cells in a table
        //table.getDefaultCell().setCellEvent(new DottedCell());
        Cell cell = null;
        cell = new Cell().add(new Paragraph("A1")).setBorder(null);
        cell.setNextRenderer(new DottedHeaderCellRenderer(cell));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("A2")).setBorder(null);
        cell.setNextRenderer(new DottedHeaderCellRenderer(cell));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("A3")).setBorder(null);
        cell.setNextRenderer(new DottedHeaderCellRenderer(cell));
        table.addCell(cell);
        // TODO Implement default renderer for all cells in a table
        // table.getDefaultCell().setCellEvent(null);
        table.addCell(new Cell().add(new Paragraph("B1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B3")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C3")).setBorder(null));
        doc.add(table);

        doc.close();
    }
}
