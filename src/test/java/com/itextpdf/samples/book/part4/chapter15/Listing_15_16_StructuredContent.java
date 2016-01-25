package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.tagging.PdfStructElem;
import com.itextpdf.core.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Ignore
@Category(SampleTest.class)
public class Listing_15_16_StructuredContent extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_16_StructuredContent.pdf";
    public static String RESOURCE
            = "./src/test/resources/book/part4/chapter15/moby.xml";

    public static void main(String args[]) throws IOException, SQLException, ParserConfigurationException, SAXException {
        new Listing_15_16_StructuredContent().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException, ParserConfigurationException, SAXException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A5);
        pdfDoc.setTagged();
        PdfStructTreeRoot root = pdfDoc.getStructTreeRoot();
        root.getRoleMap().put(new PdfName("chapter"), PdfName.Sect);
        root.getRoleMap().put(new PdfName("title"), PdfName.H);
        root.getRoleMap().put(new PdfName("para"), PdfName.P);
        PdfStructElem top = root.addKid(new PdfStructElem(pdfDoc, new PdfName("chapter")));
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        List<PdfStructElem> elements = new ArrayList<PdfStructElem>();
        parser.parse(
                new InputSource(new FileInputStream(RESOURCE)),
                new Listing_15_17_StructureParser(pdfDoc, top, elements));
        parser.parse(
                new InputSource(new FileInputStream(RESOURCE)),
                new Listing_15_18_ContentParser(pdfDoc, elements));
        pdfDoc.close();
    }
}
