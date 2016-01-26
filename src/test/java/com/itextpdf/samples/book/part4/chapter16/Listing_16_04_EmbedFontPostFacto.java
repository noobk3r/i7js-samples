package com.itextpdf.samples.book.part4.chapter16;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfObject;
import com.itextpdf.core.pdf.PdfOutputStream;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_16_04_EmbedFontPostFacto extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter16/Listing_16_04_EmbedFontPostFacto.pdf";
    public static String RESULT1
            = "./target/test/resources/book/part4/chapter16/Listing_16_04_EmbedFontPostFacto_without_font.pdf";
    public static String RESULT2
            = "./target/test/resources/book/part4/chapter16/Listing_16_04_EmbedFontPostFacto_with_font.pdf";
    public static String FONT
            = "./src/test/resources/book/part4/chapter16/wds011402.ttf";
    public static String FONTNAME
            = "WaltDisneyScriptv4.1";

    public static void main(String args[]) throws Exception {
        new Listing_16_04_EmbedFontPostFacto().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws Exception {
        createPdf(RESULT1);
        changePdf(RESULT1, RESULT2);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFontFactory.createFont(FONT, "", false);
        doc.add(new Paragraph("iText in Action").setFont(font).setFontSize(60));
        doc.close();
    }

    public void changePdf(String src, String dest) throws IOException {
        // the font file
        RandomAccessFile raf = new RandomAccessFile(FONT, "r");
        byte fontfile[] = new byte[(int) raf.length()];
        raf.readFully(fontfile);
        raf.close();
        // create a new stream for the font file
        PdfStream stream = new PdfStream(fontfile);
        // TODO No flateCompress
        stream.setCompressionLevel(PdfOutputStream.DEFAULT_COMPRESSION);
        stream.put(new PdfName("Lenght1"), new PdfNumber(fontfile.length));
        // create a reader object
        PdfReader reader = new PdfReader(src);
        int n = (int) reader.getLastXref();
        PdfObject object;
        PdfDictionary font;
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(dest));
        PdfName fontname = new PdfName(FONTNAME);
        for (int i = 0; i < n; i++) {
            object = pdfDoc.getPdfObject(i);
            if (object == null || !object.isDictionary())
                continue;
            font = (PdfDictionary) object;
            if (PdfName.FontDescriptor.equals(font.get(PdfName.Type))
                    && fontname.equals(font.get(PdfName.FontName))) {
                // TODO No addToBody
                // PdfIndirectObject objref = pdfDoc.getWriter().addToBody(stream);
                // font.put(PdfName.FontFile2, objref.getIndirectReference());
            }
        }
        pdfDoc.close();
    }
}
