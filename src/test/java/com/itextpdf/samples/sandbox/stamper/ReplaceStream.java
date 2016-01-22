/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21617218/itext-or-itextsharp-rudimentary-text-edit
 *
 * This is only a partial answer. It's a quick and dirty method showing how to
 * change a stream inside a PDF. Obviously, you'll have to detect words that are
 * stored in Form XObjects too, and you can seriously screw up the layout when
 * you manipulate the content stream as is done in this example.
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfObject;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ReplaceStream extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/replace_stream.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReplaceStream().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        PdfPage page = pdfDoc.getPage(1);
        PdfDictionary dict = page.getPdfObject();
        PdfObject object = dict.get(PdfName.Contents);
        if (object instanceof PdfStream) {
            PdfStream stream = (PdfStream) object;
            byte[] data = stream.getBytes();
            stream.setData(new String(data).replace("Hello World", "HELLO WORLD").getBytes("UTF-8"));
        }
        pdfDoc.close();
    }
}
