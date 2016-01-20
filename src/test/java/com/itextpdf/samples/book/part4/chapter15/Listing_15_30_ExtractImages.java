package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.parser.EventListener;
import com.itextpdf.core.parser.PdfContentStreamProcessor;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

@Ignore
@Category(SampleTest.class)
public class Listing_15_30_ExtractImages {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_30_ExtractImages%s.%s";
    public static final String IMAGE_TYPES
            = "./src/test/resources/book/part3/chapter10/cmp_Listing_10_09_ImageTypes.pdf";

    @Test
    public void manipulatePdf() throws IOException, SQLException, ParserConfigurationException, SAXException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(IMAGE_TYPES), new PdfWriter(new ByteArrayOutputStream()));
        EventListener listener = new Listing_15_31_MyImageRenderListener(DEST);
        PdfContentStreamProcessor parser = new PdfContentStreamProcessor(listener);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            parser.processPageContent(pdfDoc.getPage(i));
        }
        pdfDoc.close();
    }
}
