package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_10_LaunchAction extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter07/Listing_07_10_LaunchAction.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_07_10_LaunchAction().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        PdfAction action = new PdfAction().put(PdfName.S, PdfName.Launch);
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.F, new PdfName("c:/windows/notepad.exe"));
        pdfDictionary.put(PdfName.P, new PdfName("test.txt"));
        pdfDictionary.put(PdfName.O, new PdfName("open"));
        pdfDictionary.put(PdfName.D, new PdfName("D:\\"));
        action.put(PdfName.Win, pdfDictionary);
        Paragraph p = new Paragraph(new Link("Click to open test.txt in Notepad.", action));
        doc.add(p);
        doc.close();
    }
}