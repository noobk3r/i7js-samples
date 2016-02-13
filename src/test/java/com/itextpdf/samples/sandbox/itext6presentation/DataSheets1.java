/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

//package com.itextpdf.samples.sandbox.itext6presentation;
//
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.forms.PdfPageFormCopier;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class DataSheets1 {
//    public static final String DEST = "./samples/target/test/resources/sandbox/itext6presentation/data_sheets1.pdf";
//    public static final String DATASHEET = "./samples/src/test/resources/pdfs/datasheet.pdf";
//
//    public static void main(String args[]) throws IOException, SQLException {
//        new DataSheets1().manipulatePdf(DEST);
//    }
//
//    public void manipulatePdf(String dest) throws SQLException, IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        addDataSheet(pdfDoc);
//        pdfDoc.close();
//    }
//
//    public void addDataSheet(PdfDocument pdfDocResult) throws IOException, SQLException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(DATASHEET));
//        pdfDoc.copyPages(1, 1, pdfDocResult, new PdfPageFormCopier());
//        pdfDoc.close();
//    }
//}
