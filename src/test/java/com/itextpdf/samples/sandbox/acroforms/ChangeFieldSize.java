/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/24404413/increase-width-of-acrofieldsitextsharp
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// TODO problems with src-file to open. Temporary change src
@Ignore
@Category(SampleTest.class)
public class ChangeFieldSize extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/change_field_size.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeFieldSize().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        String value = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        PdfFormField fieldName = form.getField("Name");
        PdfWidgetAnnotation annotationName = fieldName.getWidgets().get(0);
        PdfArray annotationRect = annotationName.getRectangle();
        annotationRect.set(2, new PdfNumber(annotationRect.getAsFloat(2) + 20f));
        fieldName.setValue(value);
        form.getField("Company").setValue(value);
        pdfDoc.close();
    }
}
