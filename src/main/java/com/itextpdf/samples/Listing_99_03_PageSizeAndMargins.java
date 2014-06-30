package com.itextpdf.samples;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.elements.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_99_03_PageSizeAndMargins {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document with a certain page size.
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(100, 100, 10, 10, 10, 10));

        //Add paragraph to the document
        doc.add(new Paragraph("Hello")).add(new Paragraph("World"));

        //Add new page with the default page size which is 100*100 and [10, 10, 10, 10] margins for this document.
        doc.newPage();

        //Add paragraph to the document
        doc.add(new Paragraph("Hello")).add(new Paragraph("World"));

        //Add new A4 page
        doc.newPage(PageSize.A4);

        //Add paragraph to the document
        doc.add(new Paragraph("Hello")).add(new Paragraph("World"));

        //Close document
        doc.close();

    }


}
