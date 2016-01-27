/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26804092/itext-button-resize-affects-label
 * It will only work starting with iText 5.5.4 +
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.*;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ChangeButton extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/hello_button.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/change_button.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeButton().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFormField button = form.copyField("Test");
        PdfArray rect = button.getWidgets().get(0).getRectangle();
        rect.set(2, new PdfNumber(rect.getAsFloat(2) + 172));
        button.setValue("Print Amended");
        form.replaceField("Test", button);
        pdfDoc.close();
    }
}
