/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/22258598/itextsharp-move-acrofield
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ChangeFieldPosition extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/state.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/change_field_position.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeFieldPosition().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        PdfFormField fieldName = fields.get("timezone2");
        PdfWidgetAnnotation annotationName = fieldName.getWidgets().get(0);
        PdfArray annotationRect = annotationName.getRectangle();
        annotationRect.set(2, new PdfNumber(annotationRect.getAsFloat(2) - 10f));
        pdfDoc.close();
    }
}
