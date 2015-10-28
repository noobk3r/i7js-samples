package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.border.SolidBorder;
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
class CustomBorderTableRenderer extends TableRenderer {
    static boolean isNextPageTopToBeDrawn = false;
    static boolean isTopToBeDrawn = true;

    public CustomBorderTableRenderer(Table modelElement, Table.RowRange rowRange) {
        super(modelElement, rowRange);
    }

    @Override
    public void drawBorder(PdfDocument document, PdfCanvas canvas) {

        // We strongly believe that everything is fine as we believe in itext5 analog example
        CellRenderer[] firstRowRenderers = rows.get(0);
        // yLines
        for (CellRenderer cellRenderer : firstRowRenderers) {
            canvas.moveTo(cellRenderer.getOccupiedArea().getBBox().getRight(),
                    getOccupiedArea().getBBox().getBottom());
            canvas.lineTo(cellRenderer.getOccupiedArea().getBBox().getRight(),
                    getOccupiedArea().getBBox().getTop());
        }
        // the first yLine
        canvas.moveTo(firstRowRenderers[0].getOccupiedArea().getBBox().getLeft(),
                getOccupiedArea().getBBox().getBottom());
        canvas.lineTo(firstRowRenderers[0].getOccupiedArea().getBBox().getLeft(),
                getOccupiedArea().getBBox().getTop());
        canvas.stroke();

        for (CellRenderer[] cellRenderers : rows) {
            canvas.moveTo(cellRenderers[0].getOccupiedArea().getBBox().getLeft(),
                    cellRenderers[0].getOccupiedArea().getBBox().getBottom());
            canvas.lineTo(cellRenderers[cellRenderers.length - 1].getOccupiedArea().getBBox().getRight(),
                    cellRenderers[cellRenderers.length - 1].getOccupiedArea().getBBox().getBottom());
        }
        if (isTopToBeDrawn) {
            canvas.moveTo(firstRowRenderers[0].getOccupiedArea().getBBox().getLeft(),
                    firstRowRenderers[0].getOccupiedArea().getBBox().getTop());
            canvas.lineTo(firstRowRenderers[firstRowRenderers.length - 1].getOccupiedArea().getBBox().getRight(),
                    firstRowRenderers[0].getOccupiedArea().getBBox().getTop());
            isTopToBeDrawn = false;
        }
        if (isNextPageTopToBeDrawn) {
            isTopToBeDrawn = true;
            isNextPageTopToBeDrawn = false;
        }
        canvas.stroke();
    }

    @Override
    protected CustomBorderTableRenderer makeOverflowRenderer(Table.RowRange rowRange) {
        return new CustomBorderTableRenderer((Table) modelElement, rowRange);
    }

    @Override
    protected CustomBorderTableRenderer makeSplitRenderer(Table.RowRange rowRange) {
        return new CustomBorderTableRenderer((Table) modelElement, rowRange);
    }

    @Override
    protected TableRenderer[] split(int row) {
        if (null != rows.get(row)) {
            isNextPageTopToBeDrawn = true;
            CellRenderer[] curRow = rows.get(row);
            for (int i = 0; i < curRow.length; i++) {
                if (0 != rows.get(row)[0].getChildRenderers().size()) {
                    isNextPageTopToBeDrawn = false;
                    break;
                }
            }
        }
        CustomBorderTableRenderer splitRenderer = createSplitRenderer(
                new Table.RowRange(rowRange.getStartRow(), rowRange.getStartRow() + row));
        splitRenderer.rows = rows.subList(0, row);
        CustomBorderTableRenderer overflowRenderer = createOverflowRenderer(
                new Table.RowRange(rowRange.getStartRow() + row, rowRange.getFinishRow()));
        overflowRenderer.rows = rows.subList(row, rows.size());
        splitRenderer.occupiedArea = occupiedArea;
        return new TableRenderer[]{splitRenderer, overflowRenderer};
    }

    @Override
    public void draw(PdfDocument document, PdfCanvas canvas) {
        super.draw(document, canvas);

    }
}

public class CustomBorder extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/custom_border.pdf";
    public static final String TEXT = "This is some long paragraph that will be added over and over " +
            "again to prove a point. It should result in rows that are split and rows that aren't.";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CustomBorder().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Table table = new Table(2);
        table.setWidth(500);
        table.setBorder(new SolidBorder(1));
        // TODO Implement setting-for-all-cells-specific-border method
        //table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        // TODO Implement setSplitLate(boolean)
        // table.setSplitLate(false);
        Cell cell = new Cell().add(new Paragraph(TEXT));
        cell.setBorder(null);
        for (int i = 0; i < 60; ) {
            table.addCell(new Cell().add(new Paragraph("Cell " + (++i))).setBorder(null));
            table.addCell(new Cell().add(new Paragraph(TEXT)).setBorder(null));
        }
        table.setNextRenderer(new CustomBorderTableRenderer(table, new Table.RowRange(0, 59)));
        doc.add(table);

        doc.close();
    }
}
