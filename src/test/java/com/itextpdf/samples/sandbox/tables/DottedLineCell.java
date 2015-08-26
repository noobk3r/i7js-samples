package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

public class DottedLineCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/dotted_line_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DottedLineCell().manipulatePdf(DEST);
    }

    private class DottedLineTableRenderer extends TableRenderer {
        public DottedLineTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        @Override
        public void drawChildren(PdfDocument document, PdfCanvas canvas) {
            super.drawChildren(document, canvas);
            canvas.setLineDash(3f, 3f);
            // first horizontal line
            CellRenderer[] cellRenderers = rows.get(0);
            canvas.moveTo(cellRenderers[0].getOccupiedArea().getBBox().getLeft(),
                    cellRenderers[0].getOccupiedArea().getBBox().getTop());
            canvas.lineTo(cellRenderers[cellRenderers.length - 1].getOccupiedArea().getBBox().getRight(),
                    cellRenderers[cellRenderers.length - 1].getOccupiedArea().getBBox().getTop());

            for (int i = 0; i < rows.size(); i++) {
                cellRenderers = rows.get(i);
                // horizontal lines
                canvas.moveTo(cellRenderers[0].getOccupiedArea().getBBox().getX(),
                        cellRenderers[0].getOccupiedArea().getBBox().getY());
                canvas.lineTo(cellRenderers[cellRenderers.length - 1].getOccupiedArea().getBBox().getRight(),
                        cellRenderers[cellRenderers.length - 1].getOccupiedArea().getBBox().getBottom());
                // first vertical line
                Rectangle cellRect = cellRenderers[0].getOccupiedArea().getBBox();
                canvas.moveTo(cellRect.getLeft(), cellRect.getBottom());
                canvas.lineTo(cellRect.getLeft(), cellRect.getTop());
                // vertical lines
                for (int j = 0; j < cellRenderers.length; j++) {
                    cellRect = cellRenderers[j].getOccupiedArea().getBBox();
                    canvas.moveTo(cellRect.getRight(), cellRect.getBottom());
                    canvas.lineTo(cellRect.getRight(), cellRect.getTop());
                }
            }
            canvas.stroke();
        }
    }

    private class DottedLineCellRenderer extends CellRenderer {
        public DottedLineCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            canvas.setLineDash(3f, 3f);
            canvas.rectangle(this.getOccupiedArea().getBBox());
            canvas.stroke();
        }
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Table event"));
        Table table = new Table(3);
        table.setNextRenderer(new DottedLineTableRenderer(table, new Table.RowRange(0, 2)));
        // TODO Implement setting-for-all-cells-specific-border method
        // table.getDefaultCell().setBorder(Cell.NO_BORDER);
        table.addCell(new Cell().add(new Paragraph("A1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("A2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("A3")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("B3")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("C3")).setBorder(null));
        doc.add(table);
        doc.add(new Paragraph("Cell event"));
        table = new Table(1);
        Cell cell = new Cell().add(new Paragraph("Test"));
        cell.setNextRenderer(new DottedLineCellRenderer(cell));
        cell.setBorder(null);
        table.addCell(new Cell().add(cell).setBorder(null));
        doc.add(table);

        doc.close();
    }
}
