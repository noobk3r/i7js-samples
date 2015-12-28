package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
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
    /** The StringBuffer that holds the characters. */
    protected StringBuffer buf = new StringBuffer();

    /** The document to which content parsed form XML will be added. */
    protected PdfDocument pdfDoc;
    /** The canvas to which content will be written. */
    protected PdfCanvas canvas;
    /** A list with structure elements. */
    protected List<PdfStructElem> elements;
    /** The current structure element during the parsing process. */
    protected PdfStructElem current;
    /** The font used when content elements are created. */
    protected PdfFont font;

    /**
     * Creates a new ContentParser
     * @param pdfDoc
     * @param elements
     * @throws IOException
     */
    public Listing_15_18_ContentParser(PdfDocument pdfDoc, List<PdfStructElem> elements)
            throws IOException {
        this.pdfDoc = pdfDoc;
        canvas = new PdfCanvas(pdfDoc.addNewPage());
        // column.setSimpleColumn(36, 36, 384, 569);
        this.elements = elements;
        font = PdfFont.createFont("c:/windows/fonts/arial.ttf", PdfEncodings.WINANSI, true);
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        for (int i = start; i < start + length; i++) {
            if (ch[i] == '\n')
                buf.append(' ');
            else
                buf.append(ch[i]);
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("chapter".equals(qName)) return;
        current = elements.get(0);
        elements.remove(0);
        canvas.beginMarkedContent(current.getRole());
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("chapter".equals(qName)) return;
            String s = buf.toString().trim();
            buf = new StringBuffer();
            if (s.length() > 0) {
                Paragraph p = new Paragraph(s).setFont(font);
                p.setTextAlignment(Property.TextAlignment.JUSTIFIED);
                Canvas canvasModel = new Canvas(canvas, pdfDoc, new Rectangle(36, 36, 384-36, 569-36));
                canvasModel.add(p);
//                while (ColumnText.hasMoreText(status)) {
//                    canvas.endMarkedContentSequence();
//                    document.newPage();
//                    canvas.beginMarkedContentSequence(current);
//                    column.setSimpleColumn(36, 36, 384, 569);
//                    status = column.go();
                }
        canvas.endMarkedContent();
    }
}