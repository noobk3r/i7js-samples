package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

@Ignore
@Category(SampleTest.class)
public class Listing_15_26_ExtractPageContentSorted1 {
    public static final String DEST
            = "./samples/target/test/resources/book/part4/chapter15/Listing_15_26_ExtractPageContentSorted1.txt";
    public static final String PREFACE
            = "./samples/src/test/resources/book/part4/chapter15/preface.pdf";

    public static void main(String args[])
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        new Listing_15_26_ExtractPageContentSorted1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest)
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        parsePdf(PREFACE, dest);
    }

    public void parsePdf(String src, String txt) throws IOException {
        PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(new ByteArrayOutputStream()));
        // TODO No predefined strategies like LocationTextExtractionStrategy here
        // LocationTextStrategyListener listener = new LocationTextStrategyListener();
        // PdfContentStreamProcessor parser = new PdfContentStreamProcessor(listener);
        // for (int i = 1; i <= pdfDoc.getNumOfPages(); i++) {
        //     parser.processPageContent(pdfDoc.getPage(i));
        //     out.println(listener.getResultantText());
        // }
        out.flush();
        out.close();
        pdfDoc.close();
    }
}
