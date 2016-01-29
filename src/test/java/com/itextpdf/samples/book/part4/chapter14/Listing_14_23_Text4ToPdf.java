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

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_14_23_Text4ToPdf extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_23_Text4ToPdf.pdf";
    public static void main(String args[]) throws IOException, BadLocationException {
        new Listing_14_23_Text4ToPdf().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, BadLocationException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(300, 150));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        // TODO NO FontMapper
        // DefaultFontMapper mapper = new DefaultFontMapper();
        // // and map MS Gothic to the corresponding font program
        // BaseFontParameters parameters = new BaseFontParameters("c:/windows/fonts/msgothic.ttc,1");
        // parameters.encoding = BaseFont.IDENTITY_H;
        // mapper.putName("MS PGothic", parameters );
        // TODO No PdfGraphics2D
        // Graphics2D g2 = new PdfGraphics2D(canvas, 300, 150, mapper);
        // // create the text pane and print it.
        JTextPane text = Listing_14_22_TextExample4.createTextPane();
        text.setSize(new Dimension(300, 150));
        // text.print(g2);
        // g2.dispose();
        doc.close();
    }
}
