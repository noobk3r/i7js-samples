/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel.notused;

import com.itextpdf.kernel.pdf.PdfName;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Bruno Lowagie (iText Software)
 */
public class NameObject {
    public static void main(String[] args) throws UnsupportedEncodingException {
        showName(new PdfName("CustomName"));
        showName(new PdfName("Test #1 100%"));
    }
    
    public static void showName(PdfName object) {
        System.out.println("PdfName:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isName: " + object.isName());
        System.out.println("> toString: " + object.toString());
    }
}
