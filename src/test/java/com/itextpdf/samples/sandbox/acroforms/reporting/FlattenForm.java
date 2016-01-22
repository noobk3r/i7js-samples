package com.itextpdf.samples.sandbox.acroforms.reporting;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class FlattenForm extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/reporting/state.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/reporting/flatten_form.pdf";


    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FlattenForm().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.getField("name").setValue("CALIFORNIA");
        form.getField("abbr").setValue("CA");
        form.getField("capital").setValue("Sacramento");
        form.getField("city").setValue("Los Angeles");
        form.getField("population").setValue("36,961,664");
        form.getField("surface").setValue("163,707");
        form.getField("timezone1").setValue("PT (UTC-8)");
        form.getField("timezone2").setValue("-");
        form.getField("dst").setValue("YES");
        form.flatFields();
        pdfDoc.close();
    }
}
