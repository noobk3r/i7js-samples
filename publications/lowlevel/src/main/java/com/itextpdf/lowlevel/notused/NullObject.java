/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel.notused;

import com.itextpdf.kernel.pdf.PdfNull;

/**
 *
 * @author Bruno Lowagie (iText Software)
 */
public class NullObject {
    public static void main(String[] args) {
        showNull(PdfNull.PDF_NULL);
    }
    
    public static void showNull(PdfNull object) {
        System.out.println("PdfNull:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isNull: " + object.isNull());
        System.out.println("> toString: " + object.toString());
    }
}
