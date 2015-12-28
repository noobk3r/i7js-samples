package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.tagging.PdfStructElem;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Listing_15_17_StructureParser extends DefaultHandler {
    /** The top element in the PDF structure */
    protected PdfStructElem top;
    /** The list of structure elements */
    protected List<PdfStructElem> elements;
    /** The document to which content parsed form XML will be added. */
    protected PdfDocument pdfDoc;
    /** Creates a parser that will parse an XML file into a structure tree. */
    public Listing_15_17_StructureParser(PdfDocument pdfDoc, PdfStructElem top, List<PdfStructElem> elements) {
        this.top = top;
        this.elements = elements;
        this.pdfDoc = pdfDoc;
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("chapter".equals(qName)) return;
        elements.add(top.addKid(new PdfStructElem(pdfDoc, new PdfName(qName))));
    }
}
