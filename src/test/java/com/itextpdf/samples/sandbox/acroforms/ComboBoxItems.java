/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28236902/itext-combobox-width-of-selected-option-issue
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfChoiceFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ComboBoxItems extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/combo_box_items.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ComboBoxItems().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(612, 792));

        Table table = new Table(2);
        Cell cell;
        // Add rows with selectors
        String[] options = {"Choose first option", "Choose second option", "Choose third option"};
        String[] exports = {"option1", "option2", "option3"};
        table.addCell(new Cell().add(new Paragraph("Combobox:")));
        cell = new Cell();
        cell.setNextRenderer(new SelectCellRenderer(cell, "Choose first option", exports, options));
        cell.setHeight(20);
        table.addCell(cell);
        doc.add(table);

        pdfDoc.close();
    }


    private class SelectCellRenderer extends CellRenderer {
        protected String name;
        protected String[] exports;
        protected String[] options;

        public SelectCellRenderer(Cell modelElement, String name, String[] exports, String[] options) {
            super(modelElement);
            this.name = name;
            this.exports = exports;
            this.options = options;
        }

        @Override
        public void draw(DrawContext drawContext) {
            PdfFont font = null;
            try {
                font = new PdfType1Font((Type1Font) FontFactory.createFont(FontConstants.HELVETICA));

            } catch (IOException e) {
                // should do smth but in this example we'll leave catching
            }
            String[][] optionsArray = new String[options.length][];
            for (int i = 0; i < options.length; i++) {
                optionsArray[i] = new String[2];
                optionsArray[i][0] = exports[i];
                optionsArray[i][1] = options[i];
            }
            PdfChoiceFormField choice = PdfFormField.createComboBox(drawContext.getDocument(), getOccupiedAreaBBox(),
                    optionsArray, name, name);
            PdfAcroForm form = PdfAcroForm.getAcroForm(drawContext.getDocument(), true);
            choice.setFont(font);
            form.addField(choice);
            // TODO Implement BorderStyles constant
            PdfDictionary borderStyleDict = new PdfDictionary();
            borderStyleDict.put(PdfName.S, PdfName.B);
            choice.getWidgets().get(0).setBorderStyle(borderStyleDict);
            choice.setVisibility(PdfFormField.VISIBLE_BUT_DOES_NOT_PRINT);
            choice.setBorderColor(Color.GRAY);
            choice.setJustification(PdfFormField.ALIGN_CENTER);
        }
    }
}
