/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter16;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_16_01_SpecialId extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter16/Listing_16_01_SpecialId.pdf";
    public static String RESOURCE = "./src/test/resources/book/part4/chapter16/bruno.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_16_01_SpecialId().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(new Rectangle(400, 300)));
        Image img = new Image(ImageFactory.getImage(RESOURCE));
        // TODO No addToBody
//        PdfImage stream = new PdfImage(img, "", null);
//        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
//        PdfIndirectObject ref = writer.addToBody(stream);
//        img.setDirectReference(ref.getIndirectReference());
        doc.add(img);
        doc.close();
    }
}
