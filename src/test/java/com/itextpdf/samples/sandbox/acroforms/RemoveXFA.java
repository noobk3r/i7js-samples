/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27057187/setting-acrofield-text-color-in-itextsharp
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class RemoveXFA extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/reportcardinitial.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/remove_xfa.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoveXFA().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.removeXfaForm();
        Map<String, PdfFormField> fields = form.getFormFields();
        for (String name : fields.keySet()) {
            if (name.indexOf("Total") > 0) {
                fields.get(name).getWidgets().get(0).setColor(new float[]{255, 0, 0});
            }
            form.getField(name).setValue("X");
        }
        pdfDoc.close();
    }
}
