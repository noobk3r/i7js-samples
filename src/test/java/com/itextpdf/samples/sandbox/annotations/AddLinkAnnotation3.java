/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/28554413/how-to-add-overlay-text-with-link-annotations-to-existing-pdf
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
@Ignore
public class AddLinkAnnotation3 extends GenericTest {
//    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_link_annotation3.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLinkAnnotation3().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        PdfFont bold = new PdfType1Font((Type1Font) FontFactory.createFont(FontConstants.HELVETICA_BOLD));
        Link link = new Link("The Best iText Questions on StackOverflow",
                PdfAction.createURI(pdfDoc, "http://pages.itextpdf.com/ebook-stackoverflow-questions.html"));
        link.setFont(bold).setFontSize(12);
        Paragraph p = new Paragraph("Download ");
        p.add(link);
        p.add(" and discover \nmore than 200 questions and answers.");
        new Document(pdfDoc).showTextAligned(p, 30, 600, 1, Property.TextAlignment.LEFT,
                Property.VerticalAlignment.TOP, (float) Math.PI / 6);
        pdfDoc.close();
    }
}
