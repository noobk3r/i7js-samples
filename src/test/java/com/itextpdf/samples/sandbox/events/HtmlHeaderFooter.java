///*
//
//    This file is part of the iText (R) project.
//    Copyright (c) 1998-2016 iText Group NV
//
//*/
//
///**
// * This example was written by Bruno Lowagie in answer to the following question:
// * http://stackoverflow.com/questions/33394158/add-header-to-pdf-from-html-using-itext
// */
//package com.itextpdf.samples.sandbox.events;
//
//import com.itextpdf.kernel.events.Event;
//import com.itextpdf.kernel.events.IEventHandler;
//import com.itextpdf.kernel.events.PdfDocumentEvent;
//import com.itextpdf.kernel.geom.Rectangle;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfPage;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
//import com.itextpdf.layout.Canvas;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.AreaBreak;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.samples.GenericTest;
//import com.itextpdf.test.annotations.type.SampleTest;
//
//import java.io.File;
//
//import org.junit.experimental.categories.Category;
//
//@Category(SampleTest.class)
//public class HtmlHeaderFooter extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/events/generic_fields.pdf";
//    public static final String HEADER = "<table width=\"100%\" border=\"0\"><tr><td>Header</td><td align=\"right\">Some title</td></tr></table>";
//    public static final String FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Footer</td><td align=\"right\">Some title</td></tr></table>";
//
//
//    public static void main(String[] args) throws Exception {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new GenericFields().manipulatePdf(DEST);
//    }
//
//    @Override
//    protected void manipulatePdf(String dest) throws Exception  {
//
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        Document doc = new Document(pdfDoc);
//        doc.setMargins(36, 36, 72, 36);
//        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler());
//        for (int i = 0; i < 50; i++) {
//            doc.add(new Paragraph("Hello World!"));
//        }
//        doc.add(new AreaBreak());
//        doc.add(new Paragraph("Hello World!"));
//        doc.add(new AreaBreak());
//        doc.add(new Paragraph("Hello World!"));
//
//        doc.close();
//    }
//
//
//    protected class HeaderFooterHandler implements IEventHandler {
//        @Override
//        public void handleEvent(Event event) {
//            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
//            PdfDocument pdfDoc = docEvent.getDocument();
//            PdfPage page = docEvent.getPage();
//            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
//            new Canvas(canvas, pdfDoc, new Rectangle(36, 36, page.getPageSize().getWidth() - 72, 100))
//                    .add();
//        }
//    }
//}
