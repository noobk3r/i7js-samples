package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.basics.io.RandomAccessSourceFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.utils.PdfMerger;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Ignore
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
        Document doc = new Document(pdfDoc);
        PdfMerger merger = new PdfMerger(pdfDoc);
        for (int i = 0; i < 3; ) {
            PdfDocument readerDoc = new PdfDocument(new PdfReader(
                    new RandomAccessSourceFactory().createSource(renameFields(SRC, ++i)),
                    null, null, null, null, null));
            merger.addPages(readerDoc, 1, readerDoc.getNumOfPages());
            readerDoc.close();
        }
        merger.merge();
        pdfDoc.close();
    }

    protected byte[] renameFields(String src, int i) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument document = new PdfDocument(new PdfReader(new FileInputStream(src)), writer);
        PdfAcroForm form = PdfAcroForm.getAcroForm(document, true);
        Set<String> keys = new HashSet<String>(form.getFormFields().keySet());
        for (String key : keys) {
            form.getField(key).setFieldName(String.format("%s_%d", key, i));
        }
        document.close();
        return baos.toByteArray();
    }
}
