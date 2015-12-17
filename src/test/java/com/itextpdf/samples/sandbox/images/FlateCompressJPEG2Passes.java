/**
 * This sample is written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/21958449/can-itextsharp-generate-pdf-with-jpeg-images-that-are-multi-stage-filtered-both
 *
 * The question was about adding compression to an image that already used /DCTDecode
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class FlateCompressJPEG2Passes extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/images/image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/images/flate_compress_jpeg_2passes.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FlateCompressJPEG2Passes().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(new FileInputStream(SRC));
        PdfDocument pdfDoc = new PdfDocument(reader);
        PdfDictionary pageDict = pdfDoc.getPage(1).getPdfObject();
        PdfDictionary pageResources = pageDict.getAsDictionary(PdfName.Resources);
        PdfDictionary pageXObjects = pageResources.getAsDictionary(PdfName.XObject);
        PdfName imgName = pageXObjects.keySet().iterator().next();
        PdfStream imgStream = pageXObjects.getAsStream(imgName);
        imgStream.setData(reader.readStreamBytesRaw(imgStream));
        imgStream.setCompressionLevel(-1);
        PdfArray array = new PdfArray();
        array.add(PdfName.FlateDecode);
        array.add(PdfName.DCTDecode);
        imgStream.put(PdfName.Filter, array);
        pdfDoc.close();
        pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        pdfDoc.close();

    }
}
