/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/23280083/itextsharp-change-order-of-optional-content-groups
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfCatalog;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ChangeOCGOrder extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/ocg.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_ocg_order.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeOCGOrder().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        PdfCatalog catalog = pdfDoc.getCatalog();
        PdfDictionary ocProps = (PdfDictionary) catalog.getPdfObject().get(PdfName.OCProperties);
        PdfDictionary occd = (PdfDictionary) ocProps.get(PdfName.D);
        PdfArray order = occd.getAsArray(PdfName.Order);
        PdfObject nestedLayers = order.get(0);
        PdfObject nestedLayerArray = order.get(1);
        PdfObject groupedLayers = order.get(2);
        PdfObject radioGroup = order.get(3);
        order.set(0, radioGroup);
        order.set(1, nestedLayers);
        order.set(2, nestedLayerArray);
        order.set(3, groupedLayers);
        pdfDoc.close();
    }
}
