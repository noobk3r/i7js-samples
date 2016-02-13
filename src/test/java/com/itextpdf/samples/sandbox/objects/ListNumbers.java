///*
//
//    This file is part of the iText (R) project.
//    Copyright (c) 1998-2016 iText Group NV
//
//*/
//
///**
//* Example written by Bruno Lowagie in answer to:
//* http://stackoverflow.com/questions/33277998/itextpdf-numbered-list-with-no-indent
//*/
//package com.itextpdf.samples.sandbox.objects;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.Property;
//import com.itextpdf.layout.element.List;
//import com.itextpdf.layout.element.ListItem;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.samples.GenericTest;
//import com.itextpdf.test.annotations.type.SampleTest;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.junit.Ignore;
//import org.junit.experimental.categories.Category;
//
//@Ignore
//@Category(SampleTest.class)
//public class ListNumbers extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/objects/list_numbers.pdf";
//
//    public static void main(String[] args) throws IOException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new ListNumbers().manipulatePdf(DEST);
//    }
//
//    public void manipulatePdf(String dest) throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        Document doc = new Document(pdfDoc);
//        doc.add(new Paragraph("Numbered list with automatic indentation"));
//        List list1 = new List(Property.ListNumberingType.DECIMAL);
//        list1.setItemStartIndex(8);
//        for (int i = 0; i < 5; i++) {
//            list1.add("item");
//        }
//        doc.add(list1);
//        doc.add(new Paragraph("Numbered list without indentation"));
//        List list2 = new List(Property.ListNumberingType.DECIMAL);
//        list2.setItemStartIndex(8);
//        // TODO TODO TODO
//        list2.setSymbolIndent(0);
//        // list2.setAlignindent(false);
//        for (int i = 0; i < 5; i++) {
//            list2.add("item");
//        }
//        doc.add(list2);
//        doc.add(new Paragraph("Nested numbered list"));
//        List list3 = new List(Property.ListNumberingType.DECIMAL);
//        list3.setItemStartIndex(8);
//        // list3.setAlignindent(false);
//        list3.setPostSymbolText(" ");
//        for (int i = 0; i < 5; i++) {
//            list3.add("item");
//            List list = new List();
//            list.setPreSymbolText(String.valueOf(8 + i) + "_");
//            list.setPostSymbolText(" ");
//            list.add("item 1");
//            list.add("item 2");
//            list3.add((ListItem)new ListItem().add(list));
//        }
//        doc.add(list3);
//        doc.close();
//    }
//}
