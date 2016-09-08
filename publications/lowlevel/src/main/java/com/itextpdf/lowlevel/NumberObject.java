/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel;

import com.itextpdf.kernel.pdf.PdfNumber;

/**
 * @author Bruno Lowagie (iText Software)
 */
public class NumberObject {
    
    public static void main(String[] args) {
        showNumber(new PdfNumber(1));
        showNumber(new PdfNumber(1.5d));
        showNumber(new PdfNumber("abcd".getBytes()));
        showNumber(new PdfNumber("1000000000000000000".getBytes()));
        showNumber(new PdfNumber("10000000000000000000".getBytes()));
    }
    
    public static void showNumber(PdfNumber object) {
        System.out.println("PdfNumber:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isNumber: " + object.isNumber());
        System.out.println("> toString: " + object.toString());
        System.out.println("> value: " + object.getValue());
        System.out.println("> int value: " + object.intValue());
        System.out.println("> long value: " + object.longValue());
        System.out.println("> double value: " + object.doubleValue());
        System.out.println("> float value: " + object.floatValue());
    }
}
