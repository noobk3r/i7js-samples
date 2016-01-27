/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.CanvasTag;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.tagging.PdfMcrDictionary;
import com.itextpdf.core.pdf.tagging.PdfStructElem;
import com.itextpdf.core.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;
@Category(SampleTest.class)
public class Listing_15_13_ReadOutLoud extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter15/Listing_15_13_ReadOutLoud.pdf";
    public static String RESOURCE = "./src/test/resources/book/part4/chapter15/posters/0062622.jpg";

    public static void main(String args[]) throws IOException {
        new Listing_15_13_ReadOutLoud().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        pdfDoc.setTagged();

        PdfPage page = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.CP1252, false);
        PdfFont font2 = PdfFontFactory.createFont(/*"c:/windows/fonts/msgothic.ttc,1"*//*"./src/test/resources/font/ipam.ttc,1"*/
                "./src/test/resources/font/FreeSans.ttf", PdfEncodings.IDENTITY_H, true);

        PdfStructTreeRoot root = pdfDoc.getStructTreeRoot();
        PdfStructElem div = root.addKid(new PdfStructElem(pdfDoc, PdfName.Div));

        PdfStructElem span;
        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        canvas.beginText();
        canvas.moveText(36, 788);
        canvas.setFontAndSize(font, 12);
        canvas.setLeading(18);
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        canvas.showText("These are some famous movies by Stanley Kubrick: ");
        canvas.closeTag();

        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        span.setE(new PdfString("Doctor"));
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        canvas.newlineShowText("Dr.");
        canvas.closeTag();

        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        canvas.showText(" Strangelove or: How I Learned to Stop Worrying and Love the Bomb.");
        canvas.closeTag();

        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        span.setE(new PdfString("Eyes Wide Shut."));
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        canvas.newlineShowText("EWS");
        canvas.closeTag();
        canvas.endText();

        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        span.setLang(new PdfString("en-us"));
        span.setAlt(new PdfString("2001: A Space Odyssey"));
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        Image img = ImageFactory.getImage(RESOURCE);
        canvas.addImage(img, 36, 640, 100, false, false);
        canvas.closeTag();

        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        canvas.beginText();
        canvas.moveText(36, 620);
        canvas.setFontAndSize(font, 12);
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        canvas.showText("This is a movie by Akira Kurosawa: ");
        canvas.closeTag();

        span = div.addKid(new PdfStructElem(pdfDoc, PdfName.Span));
        span.setActualText(new PdfString("Seven Samurai."));
        canvas.openTag(new CanvasTag(span.addKid(new PdfMcrDictionary(page, span))));
        canvas.setFontAndSize(font2, 12);
        canvas.showText("\u4e03\u4eba\u306e\u4f8d");
        canvas.closeTag();
        canvas.endText();

        pdfDoc.close();
    }
}
