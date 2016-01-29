/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.experimental.categories.Category;

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/26325712/itext-add-image-to-existing-document-using-itext-pdfstamper
 */
@Category(SampleTest.class)
public class StampBarcode extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/superman.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/stamp_barcode.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new StampBarcode().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        int n = pdfDoc.getNumberOfPages();
        Rectangle pagesize;
        for (int i = 1; i <= n; i++) {
            PdfCanvas over = new PdfCanvas(pdfDoc.getPage(i));
            pagesize = pdfDoc.getPage(i).getPageSize();
            float x = pagesize.getLeft() + 10;
            float y = pagesize.getTop() - 50;
            BarcodeEAN barcode = new BarcodeEAN(pdfDoc);
            barcode.setCodeType(BarcodeEAN.EAN8);
            String s = String.valueOf(i);
            s = "00000000".substring(s.length()) + s;
            barcode.setCode(s);
            PdfFormXObject barcodeXObject = barcode.createFormXObject(Color.BLACK, Color.BLACK, pdfDoc);
            over.addXObject(barcodeXObject, x, y);
            System.out.println(over.toString());
        }
        pdfDoc.close();
    }
}
