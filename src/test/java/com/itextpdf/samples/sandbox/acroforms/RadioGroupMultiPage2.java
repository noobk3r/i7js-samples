/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30895930/issue-with-itext-radiocheckfield-when-displayed-on-multiple-pages
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

// TODO radio group is not working correctly DEVSIX-326

@Category(SampleTest.class)
public class RadioGroupMultiPage2 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/radio_group_multi_page2.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RadioGroupMultiPage2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "answer", "answer 1");
        Table table = new Table(2);
        Cell cell;
        for (int i = 0; i < 25; i++) {
            cell = new Cell().add(new Paragraph("Question " + i));
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("Answer " + i));
            table.addCell(cell);
        }
        for (int i = 0; i < 25; i++) {
            cell = new Cell().add(new Paragraph("Radio: " + i));
            table.addCell(cell);
            cell = new Cell();
            cell.setNextRenderer(new MyCellRenderer(cell, radioGroup, "answer " + i));
            table.addCell(cell);
        }
        doc.add(table);
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(radioGroup);
        pdfDoc.close();
    }


    private class MyCellRenderer extends CellRenderer {
        protected PdfButtonFormField radioGroup;
        protected String value;

        public MyCellRenderer(Cell modelElement, PdfButtonFormField radioGroup, String value) {
            super(modelElement);
            this.radioGroup = radioGroup;
            this.value = value;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            PdfFormField field = PdfFormField.createRadioButton(document, getOccupiedAreaBBox(), radioGroup, value);
            PdfAcroForm.getAcroForm(document, true).addFieldAppearanceToPage(field, document.getLastPage());
        }
    }

}
