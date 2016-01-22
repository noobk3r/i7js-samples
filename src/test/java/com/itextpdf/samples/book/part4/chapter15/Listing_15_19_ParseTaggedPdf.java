package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.testutils.TaggedPdfReaderTool;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

// TODO Unignore when Listing_15_16 will be revised
@Ignore
@Category(SampleTest.class)
public class Listing_15_19_ParseTaggedPdf extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter15/Listing_15_19_ParseTaggedPdf.pdf";
    public static final String STRUCTURED_CONTENT = "./src/test/resources/book/part4/chapter15/cmp_Listing_15_16_StructuredContent.pdf";


    public static void main(String args[]) throws IOException, SQLException, ParserConfigurationException, SAXException {
        new Listing_15_19_ParseTaggedPdf().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException, ParserConfigurationException, SAXException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(STRUCTURED_CONTENT));
        TaggedPdfReaderTool readertool = new TaggedPdfReaderTool(pdfDoc);
        readertool.convertToXml(new FileOutputStream(DEST));
        pdfDoc.close();
    }
}
