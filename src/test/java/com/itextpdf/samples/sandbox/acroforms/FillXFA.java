/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.acroforms;


import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.xfa.XfaForm;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;


@Category(SampleTest.class)
public class FillXFA extends GenericTest {

    public static final String SRC = "./src/test/resources/sandbox/acroforms/purchase_order.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/purchase_order_filled.pdf";
    public static final String XML = "./src/test/resources/sandbox/acroforms/data.xml";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillXFA().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument document = new PdfDocument(new PdfReader(SRC),new PdfWriter(DEST));
        PdfAcroForm form = PdfAcroForm.getAcroForm(document, true);
        XfaForm xfa = form.getXfaForm();
        xfa.fillXfaForm(new FileInputStream(XML));
        xfa.write(document);
        document.close();
    }

}
