/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28418555/remove-page-reference-from-annotation0
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddFieldAndKids extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_field_and_kids.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddExtraMargin().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        PdfFormField personal = PdfFormField.createEmptyField(pdfDoc);
        personal.setFieldName("personal");
        PdfTextFormField name =
                PdfFormField.createText(pdfDoc, new Rectangle(36, 760, 144 - 36, 790 - 760), "", "name");
        personal.addKid(name);
        PdfTextFormField password =
                PdfFormField.createText(pdfDoc, new Rectangle(150, 760, 450 - 150, 790 - 760), "", "password");
        personal.addKid(password);
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(personal, pdfDoc.getPage(1));
        pdfDoc.close();
    }
}
