package com.itextpdf.samples.signatures.chapter02;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.IOException;

public class Temp {
    public static final String OLDSRC = "./samples/src/test/resources/signatures/chapter02/hello.pdf";
    public static final String SRC = "./samples/target/test/resources/signatures/chapter02/SRCTEMP.pdf";
    public static final String DEST = "./samples/target/test/resources/signatures/chapter02/TEMP.pdf";

    public static void main(String[] args) throws IOException {
        createDest();
    }


    public static void createDest() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(OLDSRC), new PdfWriter(DEST), true);
        new PdfCanvas(pdfDoc.getFirstPage())
                .rectangle(100, 100, 100, 100)
                .stroke();
        pdfDoc.close();

    }
}
