/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30895930/issue-with-itext-radiocheckfield-when-displayed-on-multiple-pages
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class RadioGroupMultiPage1 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/radio_group_multi_page1.pdf";
    public static final String[] LANGUAGES = {"English", "German", "French", "Spanish", "Dutch"};

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RadioGroupMultiPage1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);
        Rectangle rect = new Rectangle(40, 806, 60 - 40, 788 - 806);
        PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "Language", "");
        for (int page = 1; page <= LANGUAGES.length; page++) {
            pdfDoc.addNewPage();
            PdfFormField field = PdfFormField.createRadioButton(pdfDoc, rect, radioGroup, LANGUAGES[page - 1]);
            field.setPage(page);
            doc.showTextAligned(new Paragraph(LANGUAGES[page - 1]).setFont(font).setFontSize(18),
                    70, 787, page, Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        }
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(radioGroup);
        doc.close();
    }
}
