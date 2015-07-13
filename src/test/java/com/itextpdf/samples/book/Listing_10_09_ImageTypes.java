package com.itextpdf.samples.book;

import com.itextpdf.barcodes.Barcode1D;
import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_10_09_ImageTypes extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_10_09_ImageTypes.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_10_09_ImageTypes().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        BarcodeEAN codeEAN = new BarcodeEAN(pdfDoc);
        codeEAN.setCodeType(BarcodeEAN.EAN13);
        codeEAN.setCode("9781935182610");
        Image img = new Image(codeEAN.createFormXObjectWithBarcode(null, null));
        doc.add(new Paragraph(String.format("%s is an image of type %s", "Barcode", img.getClass().getName())));
        doc.add(img);

        //@TODO Uncomment when Barcode PDF417 is implemented
//        BarcodePDF417 pdf417 = new BarcodePDF417();
//        String text = "iText in Action, a book by Bruno Lowagie.";
//        pdf417.setText(text);
//        img = pdf417.getImage();
//        doc.add(new Paragraph(
//                String.format("%s is an image of type %s", "barcode", img.getClass().getName())));
//        doc.add(img);

        //Close document
        pdfDoc.close();
    }
}
