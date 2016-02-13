///*
//
//    This file is part of the iText (R) project.
//    Copyright (c) 1998-2016 iText Group NV
//
//*/
//
///**
//* Example written by Bruno Lowagie in answer to a question on StackOverflow
//*  http://stackoverflow.com/questions/23658333/arrayindexoutofboundsexception-pdfreaderinstance-getnewobjectnumber
//*
//* When concatenating documents, we add a named destination every time
//* a new document is started. After we've finished merging, we add an extra
//* page with the table of contents and links to the named destinations.
//*/
//package com.itextpdf.samples.sandbox.merge;
//
//import com.itextpdf.basics.geom.PageSize;
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfObject;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.pdf.annot.PdfAnnotation;
//import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
//import com.itextpdf.core.pdf.canvas.PdfCanvas;
//import com.itextpdf.core.pdf.xobject.PdfFormXObject;
//import com.itextpdf.model.Document;
//import com.itextpdf.samples.GenericTest;
//import com.itextpdf.test.annotations.type.SampleTest;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//import org.junit.experimental.categories.Category;
//
//@Category(SampleTest.class)
//public class NUpWithLink extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/merge/nup_with_link.pdf";
//    public static final String SRC1 = "./src/test/resources/sandbox/merge/links1.pdf";
//    public static final String SRC2 = "./src/test/resources/sandbox/merge/links2.pdf";
//    public static void main(String[] args) throws IOException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new AddCover2().manipulatePdf(DEST);
//    }
//
//    public void manipulatePdf(String dest) throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        Document doc = new Document(pdfDoc);
//
//        float W = PageSize.A4.getWidth() / 2;
//        float H = PageSize.A4.getHeight() / 2;
//
//        int firstPage = 1;
//        String[] files = new String[]{SRC1, SRC2};
//        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
//        for (int i = 0; i < files.length; i++) {
//            PdfDocument srcDoc  = new PdfDocument(new PdfReader(files[i]));
//            // TODO currentReader.consolidateNamedDestinations();
//            for (int pageNum = 1; pageNum <= srcDoc.getNumberOfPages(); pageNum++) {
//                PdfFormXObject importedPage = srcDoc.getPage(pageNum).copyAsFormXObject(pdfDoc);
//                float a = 0.5f;
//                float e = (pageNum % 2 == 0) ? W : 0;
//                float f = (pageNum % 4 == 1 || pageNum % 4 == 2) ? H : 0;
//                Map<Object, PdfObject> map =srcDoc.getCatalog().getNamedDestinations();
//                // ArrayList<PdfAnnotation.PdfImportedLink> links = currentReader.getLinks(pageNum);
//                canvas.addXObject(importedPage, a, 0, 0, a, e, f);
//                for (Object entry : map.keySet()) {
//                    PdfObject destination = map.get(entry);
//                    PdfLinkAnnotation annot = (PdfLinkAnnotation) destination;
//
//                }
//                for (int j = 0; j < links.size(); j++) {
//                    PdfAnnotation.PdfImportedLink link = (PdfAnnotation.PdfImportedLink)links.get(j);
//                    if (link.isInternal()) {
//                        int dPage = link.getDestinationPage();
//                        int newDestPage = (dPage-1)/4 + firstPage;
//                        float ee = (dPage % 2 == 0) ? W : 0;
//                        float ff = (dPage % 4 == 1 || dPage % 4 == 2) ? H : 0;
//                        link.setDestinationPage(newDestPage);
//                        link.transformDestination(a, 0, 0, a, ee, ff);
//                    }
//                    link.transformRect(a, 0, 0, a, e, f);
//                    writer.addAnnotation(link.createAnnotation(writer));
//                }
//                if (pageNum % 4 == 0)
//                    document.newPage();
//            }
//            if (i < files.length - 1)
//                document.newPage();
//            firstPage += (currentReader.getNumberOfPages()+3)/4;
//        }
//        doc.close();
//    }
//}
