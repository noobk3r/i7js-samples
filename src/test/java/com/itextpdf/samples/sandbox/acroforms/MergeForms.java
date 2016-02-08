/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/26174675/copy-pdf-with-annotations-using-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
@Ignore("Merging forms with the same name: widget annotation of old field is removed")
public class MergeForms extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/merge_forms.pdf";
    public static final String SRC1 = "./src/test/resources/sandbox/acroforms/subscribe.pdf";
    public static final String SRC2 = "./src/test/resources/sandbox/acroforms/state.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MergeForms().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader[] readers = {
                new PdfReader(getFile1()),
                new PdfReader(getFile2())
        };
        manipulatePdf(dest, readers);
    }

    protected void manipulatePdf(String dest, PdfReader[] readers) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        for (PdfReader reader : readers) {
            PdfDocument readerDoc = new PdfDocument(reader);
            // Note that itext7 see name instead of personal.name in the first source file,
            // so the result file will have the mutual field name. See MergeForms2 to find
            // how to overcome this difficulty
            readerDoc.copyPages(1, readerDoc.getNumberOfPages(), pdfDoc, new PdfPageFormCopier());
            readerDoc.close();
        }
        pdfDoc.close();
    }

    public String getFile1() {
        return SRC1;
    }

    public String getFile2() {
        return SRC2;
    }
}
