///**
//* Example written by Bruno Lowagie in answer to:
//* http://stackoverflow.com/questions/20909450/correct-text-position-center-in-rectangle-itext
//*
//* Doing some font math to vertically fit a piece of text inside a rectangle.
//*/
//package com.itextpdf.samples.sandbox.objects;
//
//import com.itextpdf.basics.font.FontConstants;
//import com.itextpdf.basics.font.FontProgram;
//import com.itextpdf.basics.geom.Rectangle;
//import com.itextpdf.core.color.Color;
//import com.itextpdf.core.font.PdfFont;
//import com.itextpdf.core.font.PdfFontFactory;
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.pdf.canvas.PdfCanvas;
//import com.itextpdf.samples.GenericTest;
//import com.itextpdf.test.annotations.type.SampleTest;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.junit.Ignore;
//import org.junit.experimental.categories.Category;
//
//@Ignore
//@Category(SampleTest.class)
//public class FitTextInRectangle extends GenericTest {
//    public static final String DEST = "./target/test/resources/sandbox/objects/fit_text_in_rectangle.pdf";
//
//    public static void main(String[] args) throws IOException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new FitTextInRectangle().manipulatePdf(DEST);
//    }
//
//    public void manipulatePdf(String dest) throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
//
//        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
//        // the rectangle and the text we want to fit in the rectangle
//        Rectangle rect = new Rectangle(100, 150, 120, 50);
//        String text = "test";
//        PdfFont font = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);
//        // try to get max font size that fit in rectangle
//        int textHeightInGlyphSpace = font.getAscent(text, 12) - font.getDescent(text, 12);
//        float fontSize = 12f / textHeightInGlyphSpace * rect.getHeight();
//        canvas
//                .beginText()
//                .setFontAndSize(font, fontSize)
//                .moveText(rect.getLeft(), rect.getBottom() - font.getDescent(text, 12) * fontSize / FontProgram.UNITS_NORMALIZATION)
//                .showText(text)
//                .endText()
//                .stroke();
//        canvas
//                .saveState()
//                .setStrokeColor(Color.BLUE)
//                .rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight())
//                .stroke()
//                .restoreState();
//
//        pdfDoc.close();
//    }
//}
