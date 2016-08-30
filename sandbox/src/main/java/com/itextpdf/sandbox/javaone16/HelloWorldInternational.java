/**
 * Example written for JavaOne 2016.
 * Differences between iText 5 and iText 7 are discussed in the JavaOne talk
 * "Oops, I broke my API" by Raf Hens and Bruno Lowagie.
 * This is the iText 7 version of one of the examples.
 */
package com.itextpdf.sandbox.javaone16;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import java.io.File;
import java.io.IOException;

public class HelloWorldInternational {
    
    public static final String DEST
        = "results/javaone16/hello_international.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    	new HelloWorldInternational().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        try (Document document = new Document(pdf)) {
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            document.setFont(font);
            PdfFont russian = PdfFontFactory.createFont(
                "src/main/resources/fonts/FreeSans.ttf", "CP1251", true);
            PdfFont cjk = PdfFontFactory.createFont(
                "src/main/resources/fonts/NotoSansCJKsc-Regular.otf", PdfEncodings.IDENTITY_H, true);
            Paragraph p = new Paragraph("Hello World! ")
                .add(new Text("Hallo Wereld! ").setFontSize(14))
                .add(new Text("Bonjour le monde! ").setFontSize(10).setTextRise(4))
                .add(
                    new Text("\u0417\u0434\u0440\u0430\u0432\u0441\u0442\u0432\u0443\u043b\u0442\u0435 \u043c\u0438\u0440! ")
                        .setFont(russian))
                .add(new Text("\u4f60\u597d\u4e16\u754c! ").setFont(cjk))
                .add(new Text("\uc5ec\ubcf4\uc138\uc694 \uc138\uacc4!").setFont(cjk));
            document.add(p);
        }
    }
}
