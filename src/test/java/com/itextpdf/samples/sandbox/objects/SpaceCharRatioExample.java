/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/30869268/java-itext-alignment
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class SpaceCharRatioExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/space_char_ratio.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SpaceCharRatioExample().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph().
                setSpacingRatio(1).
                setTextAlignment(Property.TextAlignment.JUSTIFIED).
                setMarginLeft(20).
                setMarginRight(20).
                add("HelloWorld HelloWorld HelloWorld HelloWorld HelloWorld" +
                        "HelloWorld HelloWorldHelloWorldHelloWorldHelloWorld" +
                        "HelloWorld HelloWorld HelloWorld HelloWorldHelloWorldHelloWorld");
        doc.add(p);

        doc.close();
    }

}
