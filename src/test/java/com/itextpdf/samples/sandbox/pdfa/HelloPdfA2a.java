/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

///**
// * Example written by Bruno Lowagie
// */
//package com.itextpdf.samples.sandbox.pdfa;
//
//import com.itextpdf.core.font.PdfFont;
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.testutils.annotations.type.SampleTest;
//import com.itextpdf.model.Document;
//import com.itextpdf.model.element.Paragraph;
//import com.itextpdf.samples.GenericTest;
//import org.junit.Ignore;
//import org.junit.experimental.categories.Category;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//@Ignore
//@Category(SampleTest.class)
//public class HelloPdfA2a extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/fonts/hello_pdfa2a.pdf";
//    public static final String FONT = "./src/test/resources/sandbox/fonts/OpenSans-Regular.ttf";
//
//    public static void main(String[] args) throws Exception {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new HelloPdfA2a().manipulatePdf(DEST);
//    }
//
//    @Override
//    protected void manipulatePdf(String dest) throws Exception {
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
//        Font font = new Font(BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
//        Document document = new Document();
//        PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(dest), PdfAConformanceLevel.PDF_A_2A);
//        writer.createXmpMetadata();
//        writer.setTagged();
//        document.open();
//        document.addLanguage("en-us");
//        File file = new File("resources/data/sRGB_CS_profile.icm");
//        ICC_Profile icc = ICC_Profile
//                .getInstance(new FileInputStream(file));
//        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);
//        Paragraph p = new Paragraph("Hello World!", font);
//        document.add(p);
//        document.close();
//        pdfDoc.close();
//    }
//}
