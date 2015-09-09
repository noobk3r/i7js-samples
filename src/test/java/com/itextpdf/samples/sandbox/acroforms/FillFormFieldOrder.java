/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29633035/change-acrofields-order-in-existing-pdf-with-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.basics.io.RandomAccessSourceFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Ignore
@Category(SampleTest.class)
public class FillFormFieldOrder extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/calendar_example.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/fill_form_field_order.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillFormFieldOrder().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        go2(go1());
    }

    public byte[] go1() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)), writer);
        Document doc = new Document(pdfDoc);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("sunday_1").setValue("1");
        fields.get("sunday_2").setValue("2");
        fields.get("sunday_3").setValue("3");
        fields.get("sunday_4").setValue("4");
        fields.get("sunday_5").setValue("5");
        fields.get("sunday_6").setValue("6");
        // TODO Implement partinal flattening
        // stamper.setFormFlattening(true);
        // stamper.partialFormFlattening("sunday_1");
        // stamper.partialFormFlattening("sunday_2");
        // stamper.partialFormFlattening("sunday_3");
        // stamper.partialFormFlattening("sunday_4");
        // stamper.partialFormFlattening("sunday_5");
        // stamper.partialFormFlattening("sunday_6");
        doc.close();
        return baos.toByteArray();
    }

    public void go2(byte[] src) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(src),
                null, null, null, null, null), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("sunday_1_notes").setValue("It's Sunday today, let's go to the sea");
        fields.get("sunday_2_1").setText("It's Sunday today, let's go to the park");
        fields.get("sunday_3_1").setText("It's Sunday today, let's go to the beach");
        fields.get("sunday_4_1").setText("It's Sunday today, let's go to the woods");
        fields.get("sunday_5_1").setText("It's Sunday today, let's go to the lake");
        fields.get("sunday_6_1").setText("It's Sunday today, let's go to the river");
        // form.flatFields();
        pdfDoc.close();
    }
}
