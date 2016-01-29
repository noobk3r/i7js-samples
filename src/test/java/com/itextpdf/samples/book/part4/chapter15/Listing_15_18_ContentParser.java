/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.tagging.PdfStructElem;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;

import java.io.IOException;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Listing_15_18_ContentParser extends DefaultHandler {
    protected StringBuffer buf = new StringBuffer();
    protected PdfDocument pdfDoc;
    protected PdfCanvas canvas;
    protected List<PdfStructElem> elements;
    protected PdfStructElem current;
    protected PdfFont font;

    public Listing_15_18_ContentParser(PdfDocument pdfDoc, List<PdfStructElem> elements)
            throws IOException {
        this.pdfDoc = pdfDoc;
        canvas = new PdfCanvas(pdfDoc.addNewPage());
        // column.setSimpleColumn(36, 36, 384, 569);
        this.elements = elements;
        font = PdfFontFactory.createFont(/*"c:/windows/fonts/arial.ttf"*/"./src/test/resources/font/FreeSans.ttf",
                PdfEncodings.WINANSI, true);
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        for (int i = start; i < start + length; i++) {
            if (ch[i] == '\n')
                buf.append(' ');
            else
                buf.append(ch[i]);
        }
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("chapter".equals(qName)) return;
        current = elements.get(0);
        elements.remove(0);
        canvas.beginMarkedContent(current.getRole());
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("chapter".equals(qName)) return;
        String s = buf.toString().trim();
        buf = new StringBuffer();
        if (s.length() > 0) {
            Paragraph p = new Paragraph(s).setFont(font);
            p.setTextAlignment(Property.TextAlignment.JUSTIFIED);
            Canvas canvasModel = new Canvas(canvas, pdfDoc, new Rectangle(36, 36, 384 - 36, 569 - 36));
            canvasModel.add(p);
        }
        canvas.endMarkedContent();
    }
}