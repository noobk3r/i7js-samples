/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26804092/itext-button-resize-affects-label
 * It will only work starting with iText 5.5.4 +
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class ChangeButton extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/hello_button.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/change_button.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeButton().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        // TODO Implement handy copy constructor
        // PushbuttonField button = form.getNewPushbuttonFromField("Test");
        PdfButtonFormField oldField = (PdfButtonFormField) form.getField("Test");
        PdfArray rect = oldField.getWidgets().get(0).getRectangle();
        PdfButtonFormField button = PdfFormField.createPushButton(
                pdfDoc,
                new Rectangle(rect.getAsFloat(0), rect.getAsFloat(1), rect.getAsFloat(2) - rect.getAsFloat(0),
                        rect.getAsFloat(3) - rect.getAsFloat(1)),
                "Test",
                "Test");
        rect.set(2, new PdfNumber(rect.getAsFloat(2) + 172));
        button.getWidgets().get(0).setRectangle(rect);
        button.setValue("Print Amended");
        // TODO Implement replacing mechanism
        // form.replacePushbuttonField("Test", button.getField());
        form.removeField("Test");
        form.addField(button);
        pdfDoc.close();
    }
}
