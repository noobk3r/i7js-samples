/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel.notused;

import com.itextpdf.kernel.pdf.PdfBoolean;

/**
 * @author Bruno Lowagie (iText Software)
 */
public class BooleanObject {
    public static void main(String[] args) {
        showBoolean(PdfBoolean.TRUE);
        showBoolean(PdfBoolean.FALSE);
    }
    
    public static void showBoolean(PdfBoolean object) {
        System.out.println("PdfBoolean:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isBoolean: " + object.isBoolean());
        System.out.println("> toString: " + object.toString());
        System.out.println("> value: " + object.getValue());
    }
}
