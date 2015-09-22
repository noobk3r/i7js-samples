/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/25808367/rotate-multiline-text-with-columntext-itextsharp
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class AddRotatedTemplate extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_rotated_template.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddRotatedTemplate().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        // TODO Implement PdfTemplate
        //PdfTemplate xobject = canvas.createTemplate(80, 120);
        //ColumnText column = new ColumnText(xobject);
        // TODO Implement analog of SimpleColumn
        //column.setSimpleColumn(new Rectangle(80, 120));
//        column.addElement(new Paragraph("Some long text that needs to be distributed over several lines."));
//        column.go();
//        canvas.addTemplate(xobject, 36, 600);
//        double angle = Math.PI / 4;
//        canvas.addTemplate(xobject,
//                (float) Math.cos(angle), -(float) Math.sin(angle),
//                (float) Math.cos(angle), (float) Math.sin(angle),
//                150, 600);
//
        pdfDoc.close();
    }
}
