/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

///**
// * Example written by Bruno Lowagie in answer to:
// * http://stackoverflow.com/questions/28222277/how-can-i-generate-a-pdf-ua-compatible-pdf-with-itext
// */
//package com.itextpdf.samples.sandbox.pdfa;
//
//import com.itextpdf.basics.geom.PageSize;
//import com.itextpdf.basics.image.Image;
//import com.itextpdf.basics.image.ImageFactory;
//import com.itextpdf.core.font.PdfFont;
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfVersion;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.testutils.annotations.type.SampleTest;
//import com.itextpdf.model.Document;
//import com.itextpdf.model.Property;
//import com.itextpdf.model.element.List;
//import com.itextpdf.model.element.ListItem;
//import com.itextpdf.model.element.Paragraph;
//import com.itextpdf.model.element.Text;
//import com.itextpdf.pdfa.PdfADocument;
//import com.itextpdf.samples.GenericTest;
//import org.junit.Ignore;
//import org.junit.experimental.categories.Category;
//
//import java.io.File;
//
//@Ignore
//@Category(SampleTest.class)
//public class PdfUA extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/pdfa/PdfUA.pdf";
//    public static String FOX = "./src/test/resources/sandbox/pdfa/fox.bmp";
//    public static String DOG = "./src/test/resources/sandbox/pdfa/dog.bmp";
//    public static String FONT = "./src/test/resources/sandbox/pdfa/FreeSans.ttf";
//
//    public static void main(String[] args) throws Exception {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new PdfUA().manipulatePdf(DEST);
//    }
//
//    @Override
//    protected void manipulatePdf(String dest) throws Exception {
//        PdfDocument pdfDoc = new PdfADocument(new PdfWriter(DEST), PdfVersion.PDF_1_7);
//        Document document = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());
//        //TAGGED PDF
//        //Make document tagged
//        pdfDoc.setTagged();
//        //===============
//        //PDF/UA
//        //Set document metadata
//        // TODO There is no setViewerPreferences
//        // writer.setViewerPreferences(PdfWriter.DisplayDocTitle);
//        // TODO There is no addLanguage and addTitle
//        // document.addLanguage("en-US");
//        //document.addTitle("English pangram");
//        // TODO There is no createXmpMetadata()
//        pdfDoc.setXmpMetadata();
//        //=====================
//
//        Paragraph p = new Paragraph();
//        //PDF/UA
//        //Embed font
//        PdfFont font = PdfFont.createFont(pdfDoc, FONT, "WinAnsi", true);
//        p.setFont(font);
//        //==================
//        Text c = new Text("The quick brown ");
//        p.add(c);
//        Image i = ImageFactory.getImage(FOX);
//        // TODO One cannot wrap Image into Paragraph
//        //c = new Paragraph(i, 0, -24);
//        //PDF/UA
//        //Set alt text
//        // TODO There is no setAccessibleAttribute
//        // c.setAccessibleAttribute(PdfName.ALT, new PdfString("Fox"));
//        //==============
//        p.add(c);
//        p.add(new Text(" jumps over the lazy "));
//        i = ImageFactory.getImage(DOG);
//        // TODO One cannot wrap Image into Paragraph
//        //c = new Chunk(i, 0, -24);
//        //PDF/UA
//        //Set alt text
//        // TODO There is no setAccessibleAttribute
//        //c.setAccessibleAttribute(PdfName.ALT, new PdfString("Dog"));
//        //==================
//        p.add(c);
//        document.add(p);
//
//        p = new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n").setFont(font).setFontSize(20);
//        document.add(p);
//        List list = new List(Property.ListNumberingType.DECIMAL);
//        ListItem itemToAdd;
//        itemToAdd = new ListItem("quick");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("brown");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("fox");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("jumps");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("over");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("the");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("lazy");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        itemToAdd = new ListItem("dog");
//        itemToAdd.setFont(font).setFontSize(20);
//        list.add(itemToAdd);
//        document.add(list);
//        document.close();
//    }
//}
