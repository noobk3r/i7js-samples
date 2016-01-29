/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_14_15_Graphics2DFonts extends GenericTest {
    /** A text file containing the available AWT fonts. */
    public static final String RESULT1
            = "./target/test/resources/book/part4/chapter14/awt_fonts.txt";
    /** A text file containing the mapping of PDF and AWT fonts. */
    public static final String RESULT2
            = "./target/test/resources/book/part4/chapter14/pdf_fonts.txt";
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_15_Graphics2DFonts.pdf";
    public static void main(String args[]) throws IOException {
        new Listing_14_15_Graphics2DFonts().manipulatePdf(DEST);
    }

    /** A series of fonts that will be used. */
    public static final Font[] FONTS = {
            new Font("Serif", Font.PLAIN, 12),
            new Font("Serif", Font.BOLD, 12),
            new Font("Serif", Font.ITALIC, 12),
            new Font("SansSerif", Font.PLAIN, 12),
            new Font("Monospaced", Font.PLAIN, 12)
    };

    public void manipulatePdf(String dest) throws IOException {
        // Creates a list of the available font families in Java AWT.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamily = ge.getAvailableFontFamilyNames();
        PrintStream out1 = new PrintStream(new FileOutputStream(RESULT1));
        for (int i = 0; i < fontFamily.length; i++) {
            out1.println(fontFamily[i]);
        }
        out1.flush();
        out1.close();

        // // Creates a mapper to map fonts available for PDF creation with AWT fonts
        // // TODO No FontMapper
        // DefaultFontMapper mapper = new DefaultFontMapper();
        //mapper.insertDirectory("c:/windows/fonts/");
        // // Writes a text version of this mapper to a file
        //PrintStream out2 = new PrintStream(new FileOutputStream(RESULT2));
        //for (Entry<String,BaseFontParameters> entry : mapper.getMapper().entrySet()) {
        //    out2.println(String.format("%s: %s", entry.getKey(), entry.getValue().fontName));
        // }
        // out2.flush();
        // out2.close();

        // Creates a PDF with the text "Hello World" in different fonts.
        float width = 150;
        float height = 150;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(width, height));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

        // TODO No PDfGraphics2D
        // Graphics2D g2d = new PdfGraphics2D(cb, width, height, mapper);
        // for (int i = 0; i < FONTS.length; ) {
        //     g2d.setFont(FONTS[i++]);
        //     g2d.drawString("Hello world", 5, 24 * i);
        // }
        // g2d.dispose();
        doc.close();
    }
}
