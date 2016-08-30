/**
 * Example written for JavaOne 2016.
 * Differences between iText 5 and iText 7 are discussed in the JavaOne talk
 * "Oops, I broke my API" by Raf Hens and Bruno Lowagie.
 * This is the iText 7 version of one of the examples.
 */
package com.itextpdf.sandbox.javaone16;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.test.annotations.WrapToTest;
import java.io.File;
import java.io.IOException;

@WrapToTest
public class HelloWorldStyles {
    public static final String DEST
        = "results/javaone16/hello_styles.pdf";
    
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    	new HelloWorldStyles().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfFont code = PdfFontFactory.createFont(FontConstants.COURIER);
        Style style = new Style()
            .setFont(code)
            .setFontSize(12)
            .setFontColor(Color.RED)
            .setBackgroundColor(Color.LIGHT_GRAY);
        try (Document document = new Document(pdf)) {
            document.add(
                new Paragraph()
                    .add("In this example, named ")
                    .add(new Text("HelloWorldStyles").addStyle(style))
                    .add(", we experiment with some text in ")
                    .add(new Text("code style").addStyle(style))
                    .add("."));
        }
    }
}
