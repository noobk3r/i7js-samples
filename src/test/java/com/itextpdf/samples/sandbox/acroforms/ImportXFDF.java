/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30508966/saving-xfdf-as-pdf
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.layout.Document;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ImportXFDF extends GenericTest {
    public static final String SRC = "./src/test/resources/pdfs/Requisition_Fillable.pdf";
    public static final String XFDF = "./src/test/resources/sandbox/acroforms/data.xfdf"; // TODO Change after revision
    public static String DEST = "./target/test/resources/sandbox/acroforms/import_xfdf.pdf";

    public static void main(String[] args) throws Exception {
        new ImportXFDF().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        // TODO Implement XfdfReader
        // XfdfReader xfdf = new XfdfReader(new FileInputStream(XFDF));
        PdfReader reader = new PdfReader(SRC);
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document doc = new Document(pdfDoc);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        // TODO Implement xfdf features
        //fields.setFields(xfdf);
        form.flattenFields();
        pdfDoc.close();
    }
}
