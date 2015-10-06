/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/29388313/itext-how-to-associate-actions-with-graphical-object
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class AddLinkImages extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/primes.pdf";
    public static final String IMG = "./src/test/resources/sandbox/annotations/info.png";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_link_images.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLinkImages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Objects with links"));
        Paragraph p = new Paragraph();
        // TODO Cannot add paragraph on paragraph
//        p.add(createImage(
//                "sandbox/resources/images/info.png", "http://itextpdf.com/"));
//        p.add(createImage(
//                "sandbox/resources/images/dog.bmp", "http://pages.itextpdf.com/ebook-stackoverflow-questions.html"));
//        p.add(createImage(
//                "sandbox/resources/images/fox.bmp", "http://stackoverflow.com/q/29388313/1622493"));
//        p.add(createImage(
//                "sandbox/resources/images/butterfly.wmf", "http://stackoverflow.com/questions/tagged/itext*"));
        doc.add(p);
        pdfDoc.close();
    }

    public Paragraph createImage(String src, String url, String pdfDoc) {
        Paragraph chunk = new Paragraph().add(new Image(ImageFactory.getImage(src.getBytes())));
        // TODO Implement setAction on chunk (we can't use PdfLinkAnnotation because chunk's rectangle is not known before rendering)
        // chunk.setAction(new PdfAction(url));
        return chunk;
    }
}
