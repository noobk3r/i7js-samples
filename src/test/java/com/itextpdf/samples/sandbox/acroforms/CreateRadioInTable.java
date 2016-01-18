/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29393050/itext-radiogroup-radiobuttons-across-multiple-pdfpcells
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class CreateRadioInTable extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/create_radio_in_table.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CreateRadioInTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        PdfButtonFormField group = PdfFormField.createRadioGroup(pdfDoc, "English", "Language");
        Table table = new Table(2);
        Cell cell;
        cell = new Cell().add(new Paragraph("English"));
        table.addCell(cell);
        cell = new Cell();
        cell.setNextRenderer(new MyCellRenderer(cell, group, "english"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("French"));
        table.addCell(cell);
        cell = new Cell();
        cell.setNextRenderer(new MyCellRenderer(cell, group, "french"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("Dutch"));
        table.addCell(cell);
        cell = new Cell();
        cell.setNextRenderer(new MyCellRenderer(cell, group, "dutch"));
        table.addCell(cell);
        doc.add(table);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.addField(group);

        pdfDoc.close();
    }


    private class MyCellRenderer extends CellRenderer {
        protected String value;
        protected PdfButtonFormField radioGroup;

        public MyCellRenderer(Cell modelElement, PdfButtonFormField radioGroup, String fieldName) {
            super(modelElement);
            this.radioGroup = radioGroup;
            this.value = fieldName;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            PdfFormField field = PdfFormField.createRadioButton(document, getOccupiedAreaBBox(), radioGroup, value);
            radioGroup.addKid(field);
        }
    }
}
