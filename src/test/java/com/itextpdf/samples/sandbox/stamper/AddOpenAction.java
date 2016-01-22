/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24087786/how-to-set-zoom-level-to-pdf-using-itextsharp-5-5-0-0
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddOpenAction extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_open_action.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddOpenAction().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfArray array = new PdfArray();
        array.add(pdfDoc.getPage(1).getPdfObject());
        array.add(PdfName.XYZ);
        array.add(new PdfNumber(0));
        array.add(new PdfNumber(pdfDoc.getPage(1).getPageSize().getHeight()));
        array.add(new PdfNumber(0.75f));
        PdfDestination pdfDest = PdfDestination.makeDestination(array);
        PdfAction action = PdfAction.createGoTo(pdfDoc, pdfDest);
        pdfDoc.getCatalog().setOpenAction(action);
        pdfDoc.close();
    }
}
