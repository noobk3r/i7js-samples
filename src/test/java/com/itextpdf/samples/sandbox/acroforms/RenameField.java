/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21344750/itextsharp-renamefield-bug
 *
 * When renaming a field, you need to respect the existing hierarchy.
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class RenameField {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/subscribe.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/rename_field.pdf";
    public static List<String> cmp_result;

    static {
        cmp_result = new ArrayList<String>();
        cmp_result.add("personal");
        cmp_result.add("name");
        cmp_result.add("login");
        cmp_result.add("password");
        cmp_result.add("reason");
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    }

    @Test
    public void manipulatePdf() throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFormField login = form.getField("loginname");
        login.setFieldName("login");
        form.getFormFields().remove("loginname");
        form.getFormFields().put("login", login);
        pdfDoc.close();

        pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(DEST)));
        Map<String, PdfFormField> fields = PdfAcroForm.getAcroForm(pdfDoc, true).getFormFields();
        List<String> result = new ArrayList<>();
        for (String name : fields.keySet()) {
            System.out.println(name);
            result.add(name);
        }
        pdfDoc.close();

        Assert.assertArrayEquals(cmp_result.toArray(), result.toArray());
    }
}
