/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/28564434/why-i-cant-vertically-print-this-string-with-itext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("vertical text DEVSIX-31")
@Category(SampleTest.class)
public class VTExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/vt_example.pdf";
    public static final String FONT = "./src/test/resources/font/FreeSans.ttf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new VTExample().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        PdfFont font = PdfFontFactory.createFont(FONT, "IDENTITY-V", false);
        // TODO There is no VerticalText
        // VerticalText vt = new VerticalText(writer.getDirectContent());
        // vt.setVerticalLayout(559, 806, 770, 29, 18);
        // vt.addText(new Phrase("Hello World !!!", font));
        //vt.go();

        doc.close();
    }
}
