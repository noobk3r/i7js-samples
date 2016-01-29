/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.xfa.XfaForm;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class FillXFA2 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/xfa_form_poland.pdf";
    public static final String XML = "./src/test/resources/sandbox/acroforms/xfa_form_poland.xml";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/xfa_form_poland_filled.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillXFA2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader pdfReader = new PdfReader(SRC);
        pdfReader.setUnethicalReading(true);
        PdfDocument document = new PdfDocument(pdfReader,new PdfWriter(DEST));
        PdfAcroForm form = PdfAcroForm.getAcroForm(document, true);
        XfaForm xfa = form.getXfaForm();
        xfa.fillXfaForm(new FileInputStream(XML));
        xfa.write(document);
        document.close();
    }
}
