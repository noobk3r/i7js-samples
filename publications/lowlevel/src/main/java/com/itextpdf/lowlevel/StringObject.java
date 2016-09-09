/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.pdf.PdfString;

/**
 * @author Bruno Lowagie (iText Software)
 */
public class StringObject {
    
    public static void main(String[] args) {
        PdfString s = new PdfString("test");
        showString(s);
        s.setHexWriting(true);
        showString(s);
        showString(new PdfString("\u6d4b\u8bd5"));
        showString(new PdfString("\u6d4b\u8bd5", PdfEncodings.UNICODE_BIG));
    }
    
    public static void showString(PdfString object) {
        System.out.println("PdfString:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isString: " + object.isString());
        System.out.println("> toString: " + object.toString());
        System.out.println("> toUnicodeString: " + object.toUnicodeString());
        System.out.println("> encoding: " + object.getEncoding());
        System.out.println("> value: " + object.getValue());
        System.out.println("> value bytes: " + new String(object.getValueBytes()));
        System.out.println("> Hexadecimal format: " + object.isHexWriting());
    }
}
