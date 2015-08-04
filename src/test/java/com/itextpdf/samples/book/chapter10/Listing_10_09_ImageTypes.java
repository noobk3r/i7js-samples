package com.itextpdf.samples.book.chapter10;

import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.barcodes.BarcodePDF417;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.image.WmfImage;
import com.itextpdf.canvas.image.WmfImageHelper;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class Listing_10_09_ImageTypes extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_10_09_ImageTypes.pdf";

    /** Paths to images. */
    public static final String[] RESOURCES = {
            "bruno_ingeborg.jpg",
//            "map.jp2",
            "info.png",
            "close.bmp",
            "movie.gif",
            "butterfly.wmf",
            "animated_fox_dog.gif",
            "marbles.tif",
            "amb.jb2"
    };

    /** Path to an image. */
    public static final String RESOURCE = "src/test/resources/img/hitchcock.png";

    public static void main(String args[]) throws IOException {
        new Listing_10_09_ImageTypes().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, MalformedURLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Image img;
        for (int i = 0; i < RESOURCES.length; i++) {
            if (String.format("src/test/resources/img/%s", RESOURCES[i]).contains(".wmf")) {
                WmfImageHelper wmf = new WmfImageHelper(new WmfImage(String.format("src/test/resources/img/%s", RESOURCES[i])));
                img = new Image((PdfFormXObject) wmf.createPdfForm());
            } else {
                com.itextpdf.basics.image.Image image = ImageFactory.getImage(String.format("src/test/resources/img/%s", RESOURCES[i]));
                img = new Image(new PdfImageXObject(image));
            }


            if (img.getWidth() > 300 || img.getHeight() > 300) {
                img.scaleToFit(300, 300);
            }
            doc.add(new Paragraph(String.format("%s is an image of type %s", RESOURCES[i], img.getClass().getName())));
            doc.add(img);
        }

        //@TODO uncomment this when Itext is capable to add AWT images to document.
//        java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(RESOURCE);
//        img = new Image(new PdfImageXObject(pdfDoc, ImageFactory.getImage(awtImage.getSource().toString().getBytes())));
//        doc.add(new Paragraph(String.format("%s is an image of type %s", "java.awt.Image", img.getClass().getName())));
//        doc.add(img);

        BarcodeEAN codeEAN = new BarcodeEAN(pdfDoc);
        codeEAN.setCodeType(BarcodeEAN.EAN13);
        codeEAN.setCode("9781935182610");
        img = new Image(codeEAN.createFormXObject(null, null));
        doc.add(new Paragraph(String.format("%s is an image of type %s", "Barcode", img.getClass().getName())));
        doc.add(img);

        BarcodePDF417 pdf417 = new BarcodePDF417();
        String text = "iText in Action, a book by Bruno Lowagie.";
        pdf417.setText(text);
        PdfFormXObject xObject = pdf417.createFormXObject(null);
        img = new Image(xObject);
        doc.add(new Paragraph(String.format("%s is an image of type %s", "Barcode", img.getClass().getName())));
        doc.add(img);

        //Close document
        pdfDoc.close();
    }
}
