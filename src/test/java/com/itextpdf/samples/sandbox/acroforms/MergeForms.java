package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.utils.PdfMerger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
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
                new PdfReader(new FileInputStream(getFile1())),
                new PdfReader(new FileInputStream(getFile2()))
        };
        manipulatePdf(dest, readers);
    }

    protected void manipulatePdf(String dest, PdfReader[] readers) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfMerger merger = new PdfMerger(pdfDoc);
        for (PdfReader reader : readers) {
            PdfDocument readerDoc = new PdfDocument(reader);
            merger.addPages(readerDoc, 1, readerDoc.getNumOfPages());
            readerDoc.close();
        }
        merger.merge();
        pdfDoc.close();
    }

    public String getFile1() {
        return SRC1;
    }

    public String getFile2() {
        return SRC2;
    }
}
