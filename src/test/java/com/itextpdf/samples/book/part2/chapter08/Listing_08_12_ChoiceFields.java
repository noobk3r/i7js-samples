/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfChoiceFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("timeout")
@Category(SampleTest.class)
public class Listing_08_12_ChoiceFields extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter08/Listing_08_12_ChoiceFields.pdf";
    public static final String DEST2
            = "./target/test/resources/book/part2/chapter08/Listing_08_12_ChoiceFields_filled.pdf";

    public static final String[] LANGUAGES =
            {"English", "German", "French", "Spanish", "Dutch"};
    /**
     * An array with export values for possible languages in a choice field.
     */
    public static final String[] EXPORTVALUES =
            {"EN", "DE", "FR", "ES", "NL"};

    public static void main(String[] args) throws Exception {
        new Listing_08_12_ChoiceFields().manipulatePdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        Cell cell;
        Cell space;
        space = new Cell(1, 2);
        space.setBorder(Border.NO_BORDER);
        space.setHeight(8);
        Table table = new Table(2);
        // TODO Add facility to set default cell properties
        table.addCell(new Cell().add(new Paragraph("Language of the movie:"))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.RIGHT));
        cell = new Cell();
        cell.setNextRenderer(new ChoiceCellRenderer(cell, 1));
        table.addCell(cell);
        table.addCell(space);
        table.addCell(new Cell().add(new Paragraph("Subtitle languages:"))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.RIGHT));
        cell = new Cell();
        cell.setNextRenderer(new ChoiceCellRenderer(cell, 2));
        cell.setHeight(70);
        table.addCell(cell);
        table.addCell(space);
        table.addCell(new Cell().add(new Paragraph("Select preferred language"))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.RIGHT));
        cell = new Cell();
        cell.setNextRenderer(new ChoiceCellRenderer(cell, 3));
        table.addCell(cell);
        table.addCell(space);
        table.addCell(new Cell().add(new Paragraph("Language of the director:"))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.RIGHT));
        cell = new Cell();
        cell.setNextRenderer(new ChoiceCellRenderer(cell, 4));
        table.addCell(cell);
        doc.add(table);
        doc.close();
    }

    public void fillPdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.getField("choice_1").setValue("NL");
        PdfArray array = (PdfArray) (form.getField("choice_3")).getPdfObject().get(PdfName.Opt);
        // TODO No setListSelection
        //form.setListSelection("choice_2", new String[]{"German", "Spanish"});
        String[] languages = getOptions(array);
        String[] exportValues = getExports(array);
        int n = languages.length;
        PdfArray newOptions = new PdfArray();
        PdfArray tempArr;
        for (int i = 0; i < n; i++) {
            tempArr = new PdfArray();
            tempArr.add(new PdfString(languages[i]));
            tempArr.add(new PdfString(exportValues[i]));
            newOptions.add(tempArr);
        }
        tempArr = new PdfArray();
        tempArr.add(new PdfString("CN"));
        tempArr.add(new PdfString("Chinese"));
        newOptions.add(tempArr);

        tempArr = new PdfArray();
        tempArr.add(new PdfString("JP"));
        tempArr.add(new PdfString("Japanese"));
        newOptions.add(tempArr);

        form.getField("choice_3").setOptions(newOptions);

        form.getField("choice_3").setValue("CN");
        form.getField("choice_4").setValue("Japanese");
        pdfDoc.close();
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        createPdf(DEST);
        fillPdf(DEST, DEST2);
    }

    protected String[] getOptions(PdfArray array) {
        String[] result = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            result[i] = ((PdfArray)array.get(i)).get(0).toString();
        }
        return result;
    }

    protected String[] getExports(PdfArray array) {
        String[] result = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            result[i] = ((PdfArray)array.get(i)).get(1).toString();
        }
        return result;
    }


    protected class ChoiceCellRenderer extends CellRenderer {
        /**
         * The text field index of a TextField that needs to be added to a cell.
         */
        protected int cf = 0;

        public ChoiceCellRenderer(Cell modelElement, int cf) {
            super(modelElement);
            this.cf = cf;
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            PdfChoiceFormField text = null;
            String[][] langAndExpArray;
            String[] langArray;

            PdfDocument document = drawContext.getDocument();
            switch (cf) {
                case 1:
                    langAndExpArray = new String[LANGUAGES.length][];
                    for (int i = 0; i < LANGUAGES.length; i++) {
                        langAndExpArray[i] = new String[2];
                        langAndExpArray[i][0] = EXPORTVALUES[i];
                        langAndExpArray[i][1] = LANGUAGES[i];
                    }
                    text = PdfFormField.createComboBox(document, getOccupiedAreaBBox(), String.format("choice_%s", cf), "", langAndExpArray
                    );
                    // TODO No setChoiceSelection(int)
                    //                        text.setChoiceSelection(2);
                    break;
                case 2:
                    langArray = new String[LANGUAGES.length];
                    for (int i = 0; i < LANGUAGES.length; i++) {
                        langArray[i] = LANGUAGES[i];
                    }
                    text = PdfFormField.createList(document, getOccupiedAreaBBox(), String.format("choice_%s", cf), "", langArray
                    );
                    text.setBorderColor(Color.GREEN);
                    PdfDictionary borderDict = new PdfDictionary();
                    borderDict.put(PdfName.S, PdfName.D);
                    text.getWidgets().get(0).setBorderStyle(borderDict);
                    text.setMultiSelect(true);
                    // TODO No setChoiceSelecitons
                    // ArrayList<Integer> selections = new ArrayList<Integer>();
                    // selections.add(0);
                    // selections.add(2);
                    // text.setChoiceSelections(selections);
                    break;
                case 3:
                    text.setBorderColor(Color.RED);
                    text.setBackgroundColor(Color.GRAY);
                    langAndExpArray = new String[LANGUAGES.length][];
                    for (int i = 0; i < LANGUAGES.length; i++) {
                        langAndExpArray[i] = new String[2];
                        langAndExpArray[i][0] = EXPORTVALUES[i];
                        langAndExpArray[i][1] = LANGUAGES[i];
                    }
                    text = PdfFormField.createComboBox(document, getOccupiedAreaBBox(), String.format("choice_%s", cf), "", langAndExpArray
                    );
                    // TODO No setChoiceSelection
                    //    text.setChoiceSelection(4);
                    break;
                case 4:
                    langArray = new String[LANGUAGES.length];
                    for (int i = 0; i < LANGUAGES.length; i++) {
                        langArray[i] = LANGUAGES[i];
                    }
                    text = PdfFormField.createComboBox(document, getOccupiedAreaBBox(), String.format("choice_%s", cf), "", langArray
                    );
                    text.setFieldFlag(PdfChoiceFormField.FF_EDIT, true);
                    break;
            }
            PdfAcroForm.getAcroForm(document, true).addField(text);
        }
    }
}
