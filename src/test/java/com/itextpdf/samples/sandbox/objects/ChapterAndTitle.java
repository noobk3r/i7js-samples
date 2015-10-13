/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28431148/itext-chapter-font-overrides-paragraph-font
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ChapterAndTitle extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/chapter_and_title.pdf";
    public static final String[] ITEMS = {
            "Insurance system", "Agent", "Agency", "Agent Enrollment", "Agent Settings",
            "Appointment", "Continuing Education", "Hierarchy", "Recruiting", "Contract",
            "Message", "Correspondence", "Licensing", "Party"
    };

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChapterAndTitle().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);
        PdfFont chapterFont = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);
        PdfFont paragraphFont = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA);
        // Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        // Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Paragraph chunk = new Paragraph("This is the title").setFont(chapterFont);
        // TODO There is no Chapter or Section in itext
        // Chapter chapter = new Chapter(new Paragraph(chunk), 1);
        // chapter.setNumberDepth(0);
        // chapter.add(new Paragraph("This is the paragraph", paragraphFont));
        // doc.add(chapter);
        doc.close();
    }
}
