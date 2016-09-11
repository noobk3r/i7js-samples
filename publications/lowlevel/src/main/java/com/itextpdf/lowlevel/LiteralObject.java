/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel;

import com.itextpdf.kernel.pdf.PdfLiteral;

/**
 * @author bruno
 */
public class LiteralObject {
    public static void main(String[] args) {
        showLiteral(new PdfLiteral("<</Type/Custom/entry1 1 /entry2 (string)>>"));
    }
    
    public static void showLiteral(PdfLiteral object) {
        System.out.println("PdfLiteral:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isLiteral: " + object.isLiteral());
        System.out.println("> toString: " + object.toString());
    }
}
