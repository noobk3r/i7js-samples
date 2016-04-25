/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Listing_15_18_ContentParser extends DefaultHandler {
    protected StringBuffer buf = new StringBuffer();
    protected Document doc;
    protected PdfCanvas canvas;
    protected List<PdfStructElem> elements;
    protected PdfStructElem current;
    protected PdfFont font;
    protected PdfName role;

    public Listing_15_18_ContentParser(Document doc, List<PdfStructElem> elements)
            throws IOException {
        this.doc = doc;
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
        if ("chapter".equals(qName)) {
            return;
        }
        current = elements.get(0);
        elements.remove(0);
        role = current.getRole();
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("chapter".equals(qName)) {
            return;
        }
        String s = buf.toString().trim();
        buf = new StringBuffer();
        if (s.length() > 0) {
            Paragraph p = new Paragraph(s).setFont(font);
            p.setRole(role);
            p.setTextAlignment(Property.TextAlignment.JUSTIFIED);
            doc.add(p);
        }
    }
}