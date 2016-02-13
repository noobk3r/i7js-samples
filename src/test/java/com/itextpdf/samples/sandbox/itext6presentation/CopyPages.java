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
//
//public class CopyPages {
//    public static final String RESULT = "./samples/target/test/resources/sandbox/itext6presentation/copy_pages.pdf";
//    public static final String SOURCE = "./samples/src/test/resources/book/part1/chapter04/cmp_Listing_04_17_NestedTables.pdf";
//    public static final String SOURCE_WITH_FORM = "./samples/src/test/resources/sandbox/acroforms/state.pdf";
//
//    public static void main(String args[]) throws IOException, XMPException {
//        new CopyPages().manipulatePdf();
//    }
//
//    public void manipulatePdf() throws IOException, XMPException {
//        // Common PdfDocument logic
//        PdfDocument srcDoc = new PdfDocument(new PdfReader(SOURCE));
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(RESULT));
//
//        // Look at itext5 code to see the difference
//        XMPMeta xmp = XMPMetaFactory.create();
//        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, new PropertyOptions(PropertyOptions.ARRAY), "Hello World", null);
//        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, "XMP & Metadata");
//        xmp.appendArrayItem(XMPConst.NS_DC, PdfConst.Subject, "Metadata");
//        pdfDoc.setXmpMetadata(xmp);
//
//        // Simple way to copy needed pages
//        srcDoc.copyPages(1, srcDoc.getNumberOfPages(), pdfDoc);
//        srcDoc.close();
//
//        // Notice how simple is to copy pages with acroforms.
//        srcDoc = new PdfDocument(new PdfReader(SOURCE_WITH_FORM));
//        srcDoc.copyPages(1, srcDoc.getNumberOfPages(), pdfDoc, new PdfPageFormCopier());
//        // And even more: itext6 will try to warn you about form fields names coincidence.
//        srcDoc.copyPages(1, srcDoc.getNumberOfPages(), pdfDoc, new PdfPageFormCopier());
//        srcDoc.close();
//
//        // No problems in finding last page
//        // Most of operations are on PdfDocument (instead of PdfReader / PdfWriter mishmash)
//        pdfDoc.getCatalog().setOpenAction(PdfAction.createGoTo(PdfExplicitDestination.createFit(pdfDoc.getLastPage())));
//
//        pdfDoc.close();
//    }
//}