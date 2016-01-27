/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21342034/resizing-a-form-field-in-itextsharp
 *
 * Given a cell, how do you add a check box with a specific size at a specific position.
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class CheckboxCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/checkbox_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CheckboxCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(5);
        Cell cell;
        for (int i = 0; i < 5; i++) {
            cell = new Cell();
            cell.setNextRenderer(new CheckboxCellRenderer(cell, "cb" + i));
            cell.setHeight(50);
            table.addCell(cell);
        }

        doc.add(table);
        pdfDoc.close();
    }


    private class CheckboxCellRenderer extends CellRenderer {
        protected String name;

        public CheckboxCellRenderer(Cell modelElement, String name) {
            super(modelElement);
            this.name = name;
        }

        @Override
        public void draw(DrawContext drawContext) {
            float x = (getOccupiedAreaBBox().getLeft() + getOccupiedAreaBBox().getRight()) / 2;
            float y = (getOccupiedAreaBBox().getTop() + getOccupiedAreaBBox().getBottom()) / 2;
            Rectangle rect = new Rectangle(x - 10, y - 10, 20, 20);
            PdfButtonFormField checkBox = PdfFormField.createCheckBox(drawContext.getDocument(), rect, name, "Yes");
            PdfAcroForm.getAcroForm(drawContext.getDocument(), true).addField(checkBox);
        }
    }
}
