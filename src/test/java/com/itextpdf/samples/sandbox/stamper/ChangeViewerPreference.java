package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ChangeViewerPreference extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/OCR.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_viewer_preference.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeViewerPreference().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        // TODO there is no implemented way to do this here in itext6
        // stamper.addViewerPreference(PdfName.DUPLEX, PdfName.DUPLEXFLIPLONGEDGE);
        pdfDoc.close();
    }
}
