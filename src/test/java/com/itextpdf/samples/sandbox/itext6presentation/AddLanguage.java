//package com.itextpdf.samples.sandbox.itext6presentation;
//
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfName;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfString;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.pdf.action.PdfAction;
//import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
//
//public class AddLanguage {
//    public static final String SRC = "./samples/src/test/resources/sandbox/stamper/hello.pdf";
//    public static final String DEST = "./samples/target/test/resources/sandbox/itext6presentation/add_language.pdf";
//
//    public static void main(String[] args) throws Exception {
//        new AddLanguage().manipulatePdf(DEST);
//    }
//
//    protected void manipulatePdf(String dest) throws Exception {
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
//        pdfDoc.getCatalog().put(PdfName.Lang, new PdfString("EN"));
//
//
//
//
//
//        pdfDoc.getCatalog().setOpenAction(PdfAction.createGoTo(pdfDoc, PdfExplicitDestination.createFit(2)));
//
//
//
//
//        pdfDoc.close();
//    }
//}
