/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/20233630/itextsharp-how-to-add-a-full-line-break
 *
 * We create a Chunk and add a background color.
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class FullDottedLine extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/full_dotted_line.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FullDottedLine().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas
                .beginText()
                .moveText(36, 750)
                .setFontAndSize(PdfFontFactory.createStandardFont(FontConstants.HELVETICA), 12)
                .showText("Before dotted line")
                .stroke();
        canvas
                .saveState()
                .setLineDash(0, 4, 4 / 2)
                .setLineCapStyle(PdfCanvasConstants.LineCapStyle.ROUND)
                .moveTo(0, 700)
                .lineTo(595, 700)
                .stroke()
                .restoreState();
        canvas
                .moveTextWithLeading(0, -100)
                .showText("After dotted line")
                .endText()
                .stroke();
        pdfDoc.close();
    }
}
