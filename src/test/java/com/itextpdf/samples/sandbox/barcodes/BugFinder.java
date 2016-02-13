///*
//
//    This file is part of the iText (R) project.
//    Copyright (c) 1998-2016 iText Group NV
//
//*/
//
///**
// * This code sample was written by Bruno Lowagie in answer to this question:
// * http://stackoverflow.com/questions/35011232
// */
//package com.itextpdf.samples.sandbox.barcodes;
//
//import com.itextpdf.io.image.ImageFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Image;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.samples.GenericTest;
//import com.itextpdf.test.annotations.type.SampleTest;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//import org.junit.experimental.categories.Category;
//
//@Category(SampleTest.class)
//public class BugFinder extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/barcodes/bug_finder.pdf";
//
//    public static void main(String[] args) throws Exception {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new BarcodeInTable().manipulatePdf(DEST);
//    }
//
//    @Override
//    protected void manipulatePdf(String dest) throws Exception {
//        FileOutputStream fos = new FileOutputStream(dest);
//        PdfWriter writer = new PdfWriter(fos);
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document doc = new Document(pdfDoc);
//
//        Table table = new Table(1);
//        Cell cell = new Cell();
//        cell.add(new Image(ImageFactory.getImage("./src/test/resources/sandbox/tables/javaone2013.jpg")).setAutoScale(true));
//        table.addCell(cell);
//
//        doc.add(table);
//
//        doc.close();
//    }
//}
