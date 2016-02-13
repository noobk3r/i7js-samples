//package com.itextpdf.samples.sandbox.itext6presentation;
//
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.pdf.xobject.PdfFormXObject;
//import com.itextpdf.model.Document;
//import com.itextpdf.model.element.AreaBreak;
//import com.itextpdf.model.element.Cell;
//import com.itextpdf.model.element.Image;
//import com.itextpdf.model.element.Table;
//import com.itextpdf.samples.GenericTest;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class ImportingPages extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/itext6presentation/ImportingPages.pdf";
//    public static final String SOURCE = "./src/test/resources/cover.pdf";
//
//    public static final String MOVIE_TEMPLATES = "./src/test/resources/book/part1/chapter02/cmp_Listing_02_02_CountryChunks.pdf";
//
//    public static void main(String args[]) throws IOException, SQLException {
//        new ImportingPages().manipulatePdf(DEST);
//    }
//
//    public void manipulatePdf(String dest) throws IOException, SQLException {
//        PdfDocument resultDoc = new PdfDocument(new PdfReader(SOURCE), new PdfWriter(DEST));
//        Document doc = new Document(resultDoc);
//
//        Table table = new Table(2);
//
//        PdfDocument srcDoc = new PdfDocument(new PdfReader(MOVIE_TEMPLATES));
//
//        for (int i = 1; i <= srcDoc.getNumOfPages(); i++) {
//            PdfFormXObject header = srcDoc.getPage(i).copyAsFormXObject(resultDoc);
//            Cell cell = new Cell().add(new Image(header).setAutoScale(true));
//            table.addCell(cell);
//        }
//
//        doc.add(new AreaBreak());
//        doc.add(table);
//
//        resultDoc.close();
//        srcDoc.close();
//    }
//}
