/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

//package com.itextpdf.samples.sandbox.itext6presentation;
//
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.pdf.action.PdfAction;
//import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
//import com.itextpdf.core.xmp.PdfConst;
//import com.itextpdf.core.xmp.XMPConst;
//import com.itextpdf.core.xmp.XMPException;
//import com.itextpdf.core.xmp.XMPMeta;
//import com.itextpdf.core.xmp.XMPMetaFactory;
//import com.itextpdf.core.xmp.options.PropertyOptions;
//import com.itextpdf.forms.PdfPageFormCopier;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class CopyForms {
//    public static final String RESULT = "./samples/target/test/resources/sandbox/itext6presentation/copy_pages.pdf";
//    public static final String SOURCE_WITH_FORM_1 = "./samples/src/test/resources/sandbox/acroforms/subscribe.pdf";
//    public static final String SOURCE_WITH_FORM_2 = "./samples/src/test/resources/sandbox/acroforms/state.pdf";
//
//    public static void main(String args[]) throws IOException, SQLException, XMPException {
//        new CopyForms().manipulatePdf();
//    }
//
//    public void manipulatePdf() throws IOException, SQLException, XMPException {
//        // Common PdfDocument logic
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SOURCE_WITH_FORM_1), new PdfWriter(RESULT));
//        PdfDocument srcDoc = new PdfDocument(new PdfReader(SOURCE_WITH_FORM_2));
//
//        // Look at itext5 code to see the difference
//        XMPMeta xmp = XMPMetaFactory.create();
//        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, new PropertyOptions(PropertyOptions.ARRAY), "Hello World", null);
//        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, "XMP & Metadata");
//        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, "Metadata");
//        pdfDoc.setXmpMetadata(xmp);
//
//        // Simple way to copy pages
//        // Notice how simple is to copy pages with acroforms.
//        // And even more: itext6 will try to handle form fields coincidence
//        srcDoc.copyPages(1, srcDoc.getNumOfPages(), pdfDoc, new PdfPageFormCopier());
//
//        // No problems in finding last page
//        // Most of operations are on PdfDocument (instead of PdfReader / PdfWriter mishmash)
//        pdfDoc.getCatalog().setOpenAction(PdfAction.createGoTo(pdfDoc, PdfExplicitDestination.createFit(pdfDoc.getLastPage())));
//
//        pdfDoc.close();
//        srcDoc.close();
//    }
//}