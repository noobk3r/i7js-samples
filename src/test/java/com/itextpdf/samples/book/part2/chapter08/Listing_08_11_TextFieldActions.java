/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfBoolean;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_08_11_TextFieldActions extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter08/Listing_08_11_TextFieldActions.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_08_11_TextFieldActions().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        pdfDoc.addNewPage();

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.put(PdfName.NeedAppearances, new PdfBoolean(true));

        PdfTextFormField date = PdfFormField.createText(pdfDoc, new Rectangle(36, 780, 90, 26)).
            setFieldName("date").
            //TODO .setBorderColor(new GrayColor(0.2f));
            setAdditionalAction(PdfName.V, PdfAction.createJavaScript(
                    "AFDate_FormatEx( 'dd-mm-yyyy' );"));

        form.addField(date);

        PdfTextFormField name = PdfFormField.createText(pdfDoc, new Rectangle(130, 780, 126, 26)).
                setFieldName("name").
                // TODO name.setBorderColor(new GrayColor(0.2f));
                setAdditionalAction(PdfName.Fo, PdfAction.createJavaScript("app.alert('name field got the focus');")).
                setAdditionalAction(PdfName.Bl, PdfAction.createJavaScript("app.alert('name lost the focus');")).
                setAdditionalAction(PdfName.K, PdfAction.createJavaScript("event.change = event.change.toUpperCase();"));

        form.addField(name);

        //Close document
        pdfDoc.close();
    }
}
