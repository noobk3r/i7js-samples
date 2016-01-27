/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part3.chapter12;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfDocumentInfo;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.PdfConst;
import com.itextpdf.core.xmp.XMPConst;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.core.xmp.XMPMeta;
import com.itextpdf.core.xmp.XMPMetaFactory;
import com.itextpdf.core.xmp.options.PropertyOptions;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_12_04_MetadataXmp extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part3/chapter12/Listing_12_04_MetadataXmp.pdf";
    public static final String RESULT1
            = "./target/test/resources/book/part3/chapter12/Listing_12_04_MetadataXmp_xmp_metadata.pdf";
    public static final String RESULT2
            = "./target/test/resources/book/part3/chapter12/Listing_12_04_MetadataXmp_xmp_metadata_automatic.pdf";
    public static final String RESULT3
            = "./target/test/resources/book/part3/chapter12/Listing_12_04_MetadataXmp_xmp_metadata_added.pdf";
    public static final String RESULT4
            = "./target/test/resources/book/part3/chapter12/Listing_12_04_MetadataXmp_xmp.xml";

    public static void main(String args[]) throws IOException, XMPException {
        new Listing_12_04_MetadataXmp().manipulatePdf(DEST);
    }

    public void createPdf(String dest) throws IOException, XMPException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        XMPMeta xmp = XMPMetaFactory.create();
        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, new PropertyOptions(PropertyOptions.ARRAY), "Hello World", null);
        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, "XMP & Metadata");
        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, "Metadata");
        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Keywords, new PropertyOptions(PropertyOptions.ARRAY), "Hello World, XMP, Metadata", null);
        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Version, new PropertyOptions(PropertyOptions.ARRAY), "1.4", null);
        pdfDoc.setXmpMetadata(xmp);

        doc.add(new Paragraph("Hello World"));

        doc.close();
    }

    public void createPdfAutomatic(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        PdfDocumentInfo info = pdfDoc.getInfo();
        info
                .setTitle("Hello World example")
                .setSubject("This example shows how to add metadata & XMP")
                .setKeywords("Metadata, iText, step 3")
                .setCreator("My program using 'iText'")
                .setAuthor("Bruno Lowagie & Paulo Soares");

        doc.add(new Paragraph("Hello World"));
        doc.close();
    }

    public void manipulatePdf(String src, String dest) throws IOException, XMPException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfDocumentInfo info = pdfDoc.getInfo();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // TODO
        // pdfDoc.setXmpMetadata();
        pdfDoc.close();
    }

    /**
     * Reads the XML stream inside a PDF file into an XML file.
     * @param src  A PDF file containing XMP data
     * @param dest XML file containing the XMP data extracted from the PDF
     * @throws IOException
     */
    public void readXmpMetadata(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src));
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] b = pdfDoc.getXmpMetadata().getBytes();
        fos.write(b, 0, b.length);
        fos.flush();
        fos.close();
        pdfDoc.close();
    }

    public void manipulatePdf(String dest) throws IOException, XMPException {
        createPdf(RESULT1);
        createPdfAutomatic(RESULT2);
        // TODO Make cmp
        new Listing_12_01_MetadataPdf().createPdf(Listing_12_01_MetadataPdf.DEST);
        manipulatePdf(Listing_12_01_MetadataPdf.DEST, RESULT3);
        // readXmpMetadata(RESULT3, RESULT4);
    }
}
