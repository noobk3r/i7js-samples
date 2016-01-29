/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/26174675/copy-pdf-with-annotations-using-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.io.source.RandomAccessSourceFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class MergeForms2 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/state.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/merge_forms2.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MergeForms2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        for (int i = 0; i < 3; ) {
            PdfDocument readerDoc = new PdfDocument(new PdfReader(
                    new RandomAccessSourceFactory().createSource(renameFields(SRC, ++i)),
                    null, null, null, null, null));
            readerDoc.copyPages(1, readerDoc.getNumberOfPages(), pdfDoc, new PdfPageFormCopier());
            readerDoc.close();
        }
        pdfDoc.close();
    }

    protected byte[] renameFields(String src, int i) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(src)), writer);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Set<String> keys = new HashSet<>(form.getFormFields().keySet());
        for (String key : keys) {
            form.getField(key).setFieldName(String.format("%s_%d", key, i));
        }
        pdfDoc.close();
        return baos.toByteArray();
    }
}
