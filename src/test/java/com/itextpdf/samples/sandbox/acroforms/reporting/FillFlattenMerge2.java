package com.itextpdf.samples.sandbox.acroforms.reporting;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
// TODO This file is exactly like FillFlattenMerge1 because there is neither PdfCopy nor PdfSmartCopy in itext6
public class FillFlattenMerge2 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/reporting/state.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/reporting/fill_flatten_merge2.pdf";
    public static final String DATA = "./src/test/resources/sandbox/acroforms/reporting/united_states.csv";


    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillFlattenMerge2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));

        ByteArrayOutputStream baos;
        PdfReader reader;
        PdfDocument pdfInnerDoc;
        Map<String, PdfFormField> fields;
        PdfAcroForm form;
        StringTokenizer tokenizer;
        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            // create a PDF in memory
            baos = new ByteArrayOutputStream();
            reader = new PdfReader(SRC);
            pdfInnerDoc = new PdfDocument(reader, new PdfWriter(baos));
            form = PdfAcroForm.getAcroForm(pdfInnerDoc, true);
            tokenizer = new StringTokenizer(line, ";");
            fields = form.getFormFields();
            fields.get("name").setValue(tokenizer.nextToken());
            fields.get("abbr").setValue(tokenizer.nextToken());
            fields.get("capital").setValue(tokenizer.nextToken());
            fields.get("city").setValue(tokenizer.nextToken());
            fields.get("population").setValue(tokenizer.nextToken());
            fields.get("surface").setValue(tokenizer.nextToken());
            fields.get("timezone1").setValue(tokenizer.nextToken());
            fields.get("timezone2").setValue(tokenizer.nextToken());
            fields.get("dst").setValue(tokenizer.nextToken());
            form.flatFields();
            pdfInnerDoc.close();

            pdfInnerDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
            pdfInnerDoc.copyPages(1, pdfInnerDoc.getNumberOfPages(), pdfDoc, new PdfPageFormCopier());
            pdfInnerDoc.close();
        }
        br.close();
        pdfDoc.close();
    }
}
