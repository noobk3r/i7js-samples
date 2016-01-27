/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19698771/checking-off-pdf-checkbox-with-itextsharp
 *
 * Given a check box in a form, how do we know which values to use in setField?
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;

import java.io.FileInputStream;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)

public class CheckBoxValues {
    public static String SRC = "./src/test/resources/sandbox/acroforms/datasheet.pdf";
    public static String DEST = "./target/test/resources/sandbox/acroforms/check_box_values.pdf";
    public static final String FIELD = "CP_1";

    @BeforeClass
    public static void beforeClass() throws Exception {
        new CheckBoxValues().manipulatePdf();
    }

    @Test
    public void manipulatePdf() throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        PdfFormField field = fields.get(FIELD);
        StringBuilder sb = new StringBuilder();
        String[] states = field.getAppearanceStates();
        for (String state : states) {
            sb.append(state);
            sb.append('\n');
        }
        System.out.println(sb);
        pdfDoc.close();
    }
}
