/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29702986/how-to-check-a-checkbox-in-pdf-file-with-the-same-variable-name-with-itext-and-j
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FillHybridForm extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/f8966.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/fill_hybrid_form.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillHybridForm().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        // TODO Implement Xfa and its removing
        // form.removeXfa();
        form.getField("p2_cb01[0]").setValue("5");
        form.getField("p2_cb01[1]").setValue("5");
        form.getField("p2_cb01[2]").setValue("5");
        form.getField("p2_cb01[3]").setValue("5");
        form.getField("p2_cb01[4]").setValue("5");
        form.getField("p2_cb01[5]").setValue("5");
        pdfDoc.close();
    }
}
