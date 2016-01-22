/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/31335715/cannot-show-special-character-in-acro-form-field
 *
 * Sometimes you need to change the font of a field.
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.core.font.PdfFont;
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
public class FillFormSpecialChars2 extends GenericTest {

    public static final String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/fill_form_special_chars2.pdf";
    public static final String FONT = "./src/test/resources/sandbox/acroforms/FreeSans.ttf";



    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillFormSpecialChars2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.setGenerateAppearance(true);
        PdfFont font = PdfFont.createFont(FONT, PdfEncodings.IDENTITY_H);


        form.getFormFields().get("Name").setValue("\u04e711111", font, 12);
        form.flatFields();
        pdfDoc.close();
    }
}
