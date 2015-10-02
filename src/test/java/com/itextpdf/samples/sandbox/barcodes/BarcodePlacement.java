package com.itextpdf.samples.sandbox.barcodes;

import com.itextpdf.barcodes.BarcodePDF417;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class BarcodePlacement extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/barcodes/barcode_placement.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new BarcodePlacement().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Image img = createBarcode(1, 1, pdfDoc);
        doc.add(new Paragraph(String.format("This barcode measures %s by %s user units",
                img.getImageScaledWidth(), img.getImageScaledHeight())));
        doc.add(img);
        img = createBarcode(3, 3, pdfDoc);
        doc.add(new Paragraph(String.format("This barcode measures %s by %s user units",
                img.getImageScaledWidth(), img.getImageScaledHeight())));
        doc.add(img);
        img = createBarcode(3, 1, pdfDoc);
        doc.add(new Paragraph(String.format("This barcode measures %s by %s user units",
                img.getImageScaledWidth(), img.getImageScaledHeight())));
        doc.add(img);

        doc.close();
    }

    // IMPORTANT We've changed the order of arguments (in comparison with itext5 example) to make it more clear
    public Image createBarcode(float mw, float mh, PdfDocument pdfDoc) {
        BarcodePDF417 barcode = new BarcodePDF417();
        barcode.setCode("BarcodePDF417 barcode");
        return new Image(barcode.createFormXObject(Color.BLACK, pdfDoc)).scale(mw, mh);
    }
}
