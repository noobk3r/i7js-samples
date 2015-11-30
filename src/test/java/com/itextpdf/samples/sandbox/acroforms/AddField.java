/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27206327/itext-add-new-acrofields-form-feilds-in-to-a-pdf-using-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddField extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/add_field.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddField().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfButtonFormField button = PdfFormField.createPushButton(pdfDoc, new Rectangle(36, 700, 36, 30), "post", "POST");
        button.setBackgroundColor(Color.GRAY);
        //@TODO (DEVSIX-233)
        //button.setValue("POST");
        button.setAction(PdfAction.createSubmitForm(pdfDoc, "http://itextpdf.com:8180/book/request", null, PdfAction.SUBMIT_HTML_FORMAT | PdfAction.SUBMIT_COORDINATES));
        button.setVisibility(PdfFormField.VISIBLE_BUT_DOES_NOT_PRINT);
        form.addField(button);


        pdfDoc.close();
    }
}
