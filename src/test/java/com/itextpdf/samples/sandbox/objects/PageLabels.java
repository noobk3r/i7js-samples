package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfTextAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class PageLabels extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/page_labels.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PageLabels().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        // TODO There are no viewer preferences (setWiewerPreferences for example)
        // writer.setViewerPreferences(PdfWriter.PageLayoutTwoPageLeft | PdfWriter.PageModeUseThumbs);
        // writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
        // TODO There is no PdfPageLabels class
        // PdfPageLabels labels = new PdfPageLabels();
        // labels.addPageLabel(1, PdfPageLabels.UPPERCASE_LETTERS);
        // labels.addPageLabel(3, PdfPageLabels.DECIMAL_ARABIC_NUMERALS);
        // labels.addPageLabel(4,
        //         PdfPageLabels.DECIMAL_ARABIC_NUMERALS, "Custom-", 2);
        //writer.setPageLabels(labels);
        doc.add(new Paragraph("Hello World"));
        doc.add(new Paragraph("Hello People"));
        pdfDoc.addNewPage();

        // we add the text to the direct content, but not in the right order
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.beginText();
        canvas.setFontAndSize(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA), 12);
        canvas.moveText(88.66f, 788);
        canvas.showText("ld");
        canvas.moveText(-22f, 0);
        canvas.showText("Wor");
        canvas.moveText(-15.33f, 0);
        canvas.showText("llo");
        canvas.moveText(-15.33f, 0);
        canvas.showText("He");
        canvas.endText();
        // we also add text in a form XObject
        // TODO There is no PdfTemplate
        // PdfTemplate tmp = canvas.createTemplate(250, 25);
        // tmp.beginText();
        // tmp.setFontAndSize(bf, 12);
        // tmp.moveText(0, 7);
        // tmp.showText("Hello People");
        // tmp.endText();
        // canvas.addTemplate(tmp, 36, 763);

        pdfDoc.getFirstPage().setMediaBox(new PageSize(PageSize.A4).rotate());
        pdfDoc.addNewPage();
        doc.add(new Paragraph("Hello World"));

        pdfDoc.addNewPage();
        pdfDoc.getPage(2).setMediaBox(new PageSize(842, 595));
        doc.add(new Paragraph("Hello World"));

        pdfDoc.addNewPage();
        pdfDoc.getPage(3).setMediaBox(PageSize.A4);
        pdfDoc.getPage(3).setCropBox(new Rectangle(40, 40, 565, 795));
        doc.add(new Paragraph("Hello World"));

        pdfDoc.addNewPage();
        pdfDoc.getPage(4).setCropBox(new Rectangle(0, 0, 0, 0));
        // TODO There is no PdfName.UserUnit constant
        //pdfDoc.getPage(4).getPdfObject().put(PdfName.USERUNIT, new PdfNumber(5));
        // writer.addPageDictEntry(PdfName.USERUNIT, new PdfNumber(5));
        doc.add(new Paragraph("Hello World"));

        pdfDoc.addNewPage();
        pdfDoc.getPage(5).setArtBox(new Rectangle(36, 36, 559, 806));
        Link link = new Link("World", PdfAction.createURI(pdfDoc, "http://maps.google.com"));
        Paragraph p = new Paragraph("Hello ");
        p.add(link);
        doc.add(p);
        PdfTextAnnotation a = new PdfTextAnnotation(pdfDoc,
                new Rectangle(10, 10, 30, 30))
                .setTitle(new PdfString("Example"))
                .setContents("This is a post-it annotation");
        pdfDoc.getPage(5).addAnnotation(a);

        doc.close();
    }
}
