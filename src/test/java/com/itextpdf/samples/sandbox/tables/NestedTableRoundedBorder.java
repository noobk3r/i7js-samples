/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class NestedTableRoundedBorder extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/nested_table_rounded_border.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NestedTableRoundedBorder().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Cell cell;
        // outer table
        Table outertable = new Table(1);
        // inner table 1
        Table innertable = new Table(new float[]{8, 12, 1, 4, 12});
        innertable.setWidthPercent(100);
        // first row
        // column 1
        cell = new Cell().add("Record Ref:");
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 2
        cell = new Cell().add("GN Staff");
        cell.setPaddingLeft(2);
        innertable.addCell(cell);
        // column 3
        cell = new Cell();
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 4
        cell = new Cell().add("Date: ");
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 5
        cell = new Cell().add("30/4/2015");
        cell.setPaddingLeft(2);
        innertable.addCell(cell);
        // spacing
        cell = new Cell(1, 5);
        cell.setHeight(3);
        cell.setBorder(null);
        innertable.addCell(cell);
        // second row
        // column 1
        cell = new Cell().add("Hospital:");
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 2
        cell = new Cell().add("Derby Royal");
        cell.setPaddingLeft(2);
        innertable.addCell(cell);
        // column 3
        cell = new Cell();
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 4
        cell = new Cell().add("Ward: ");
        cell.setBorder(null);
        cell.setPaddingLeft(5);
        innertable.addCell(cell);
        // column 5
        cell = new Cell().add("21");
        cell.setPaddingLeft(2);
        innertable.addCell(cell);
        // spacing
        cell = new Cell(1, 5);
        cell.setHeight(3);
        cell.setBorder(null);
        innertable.addCell(cell);
        // first nested table
        cell = new Cell().add(innertable);
        cell.setNextRenderer(new RoundedBorderCellRenderer(cell));
        cell.setBorder(null);
        cell.setPadding(8);
        outertable.addCell(cell);
        // inner table 2
        innertable = new Table(new float[]{3, 17, 1, 16});
        innertable.setWidthPercent(100);
        // first row
        // column 1
        cell = new Cell();
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 2
        cell = new Cell().add("Name");
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 3
        cell = new Cell();
        cell.setBorder(null);
        innertable.addCell(cell);
        // column 4
        cell = new Cell().add("Signature: ");
        cell.setBorder(null);
        innertable.addCell(cell);
        // spacing
        cell = new Cell(1, 4);
        cell.setHeight(3);
        cell.setBorder(null);
        innertable.addCell(cell);
        // subsequent rows
        for (int i = 1; i < 4; i++) {
            // column 1
            cell = new Cell().add(String.format("%s:", i));
            cell.setBorder(null);
            innertable.addCell(cell);
            // column 2
            cell = new Cell();
            innertable.addCell(cell);
            // column 3
            cell = new Cell();
            cell.setBorder(null);
            innertable.addCell(cell);
            // column 4
            cell = new Cell();
            innertable.addCell(cell);
            // spacing
            cell = new Cell(1, 4);
            cell.setHeight(3);
            cell.setBorder(null);
            innertable.addCell(cell);
        }
        // second nested table
        cell = new Cell().add(innertable);
        cell.setNextRenderer(new RoundedBorderCellRenderer(cell));
        cell.setBorder(null);
        cell.setPaddingLeft(8);
        cell.setPaddingTop(8);
        cell.setPaddingRight(8);
        cell.setPaddingBottom(8);
        outertable.addCell(cell);
        // add the table
        doc.add(outertable);

        doc.close();
    }


    private class RoundedBorderCellRenderer extends CellRenderer {
        public RoundedBorderCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(DrawContext drawContext) {
            drawContext.getCanvas().roundRectangle(getOccupiedAreaBBox().getX() + 1.5f, getOccupiedAreaBBox().getY() + 1.5f,
                    getOccupiedAreaBBox().getWidth() - 3, getOccupiedAreaBBox().getHeight() - 3, 4);
            drawContext.getCanvas().stroke();
            super.draw(drawContext);
        }
    }
}
