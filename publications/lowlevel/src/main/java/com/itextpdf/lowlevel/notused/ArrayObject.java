/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel.notused;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNull;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;

/**
 *
 * @author Bruno Lowagie (iText Software)
 */
public class ArrayObject {
    public static void main(String[] args) {
        PdfArray array = new PdfArray();
        array.add(PdfName.First);
        array.add(new PdfString("Second"));
        array.add(new PdfNumber(3));
        array.add(PdfBoolean.FALSE);
        showArray(array);
    }
    
    public static void showArray(PdfArray object) {
        System.out.println("PdfName:");
        System.out.println("> type: " + (int)object.getType());
        System.out.println("> isArray: " + object.isArray());
        System.out.println("> toString: " + object.toString());
        System.out.println("> size: " + object.size());
        PdfObject o;
        for (int i = 0; i < object.size(); i++) {
            o = object.get(i);
            switch (o.getType()) {
                case PdfObject.ARRAY:
                    ArrayObject.showArray((PdfArray)o);
                    break;
                case PdfObject.BOOLEAN:
                    BooleanObject.showBoolean((PdfBoolean)o);
                    break;
                case PdfObject.DICTIONARY:
                    DictionaryObject.showDictionary((PdfDictionary)o);
                    break;
                case PdfObject.NAME:
                    NameObject.showName((PdfName)o);
                    break;
                case PdfObject.NULL:
                    NullObject.showNull((PdfNull)o);
                    break;
                case PdfObject.NUMBER:
                    NumberObject.showNumber((PdfNumber)o);
                    break;
                case PdfObject.STREAM:
                    StreamObject.showStream((PdfStream)o);
                    break;
                case PdfObject.STRING:
                    StringObject.showString((PdfString)o);
                    break;
            }
        }
    }
}
