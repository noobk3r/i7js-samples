/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel.chapter01;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNull;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;

/**
 * @author Bruno Lowagie (iText Software)
 */
public class ShowObjects {
    
    public static void showArray(PdfArray object) {
        System.out.println("PdfName:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isArray: " + object.isArray());
        System.out.println("> toString: " + object.toString());
        System.out.println("> size: " + object.size());
        for (int i = 0; i < object.size(); i++) {
            showObject(object.get(i, false));
        }
    }
    
    public static void showBoolean(PdfBoolean object) {
        System.out.println("PdfBoolean:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isBoolean: " + object.isBoolean());
        System.out.println("> toString: " + object.toString());
        System.out.println("> value: " + object.getValue());
    }
    
    public static void showDictionary(PdfDictionary object) {
        System.out.println("PdfDictionary:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isDictionary: " + object.isDictionary());
        System.out.println("> toString: " + object.toString());
        System.out.println("> size: " + object.size());
        for (PdfName name : object.keySet()) {
            System.out.print(name.toString());
            System.out.print(": ");
            showObject(object.get(name, false));
        }
    }
    
    public static void showLiteral(PdfLiteral object) {
        System.out.println("PdfLiteral:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isLiteral: " + object.isLiteral());
        System.out.println("> toString: " + object.toString());
    }
    
    public static void showIndirectReference(PdfIndirectReference object) {
        System.out.println("PdfIndirectReference:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isIndirectReference: " + object.isIndirectReference());
        System.out.println("> toString: " + object.toString());
        System.out.println("> object number: " + object.getObjNumber());
        System.out.println("> generation number: " + object.getGenNumber());
    }
    
    public static void showName(PdfName object) {
        System.out.println("PdfName:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isName: " + object.isName());
        System.out.println("> toString: " + object.toString());
    }
    
    public static void showNull(PdfNull object) {
        System.out.println("PdfNull:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isNull: " + object.isNull());
        System.out.println("> toString: " + object.toString());
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
    
    public static void showStream(PdfStream object) {
        System.out.println("PdfDictionary:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isStream: " + object.isStream());
        System.out.println("> toString: " + object.toString());
        System.out.println("> length: " + object.getLength());
        System.out.println("> content: " + new String(object.getBytes(false)));
        System.out.println("> decoded content: " + new String(object.getBytes()));
        for (PdfName name : object.keySet()) {
            System.out.print(name.toString());
            System.out.print(": ");
            showObject(object.get(name, false));
        }
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
    
    public static void showObject(PdfObject object) {
        switch (object.getType()) {
            case PdfObject.ARRAY:
                showArray((PdfArray)object);
                break;
            case PdfObject.BOOLEAN:
                showBoolean((PdfBoolean)object);
                break;
            case PdfObject.DICTIONARY:
                showDictionary((PdfDictionary)object);
                break;
            case PdfObject.LITERAL:
                showLiteral((PdfLiteral)object);
                break;
            case PdfObject.INDIRECT_REFERENCE:
                showIndirectReference((PdfIndirectReference)object);
                break;
            case PdfObject.NAME:
                showName((PdfName)object);
                break;
            case PdfObject.NULL:
                showNull((PdfNull)object);
                break;
            case PdfObject.NUMBER:
                showNumber((PdfNumber)object);
                break;
            case PdfObject.STREAM:
                showStream((PdfStream)object);
                break;
            case PdfObject.STRING:
                showString((PdfString)object);
                break;
            default:
                System.out.println("NOT A KNOWN OBJECT");
        }
    }
}
