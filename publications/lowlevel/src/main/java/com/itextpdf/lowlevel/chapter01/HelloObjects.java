/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 the ABC of PDF
 */
package com.itextpdf.lowlevel.chapter01;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import java.io.IOException;

/**
 * @author Bruno Lowagie (iText Software)
 */
public class HelloObjects {
    public static final String PATH = "src/main/resources/pdfs/hello_world.pdf";
    
    public static void main(String[] args) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfReader(PATH));
        
        PdfDictionary trailer = pdf.getTrailer();
        PdfDictionary root = trailer.getAsDictionary(PdfName.Root);
        PdfDictionary markInfo = root.getAsDictionary(PdfName.MarkInfo);
        
        ShowObjects.showBoolean(markInfo.getAsBoolean(PdfName.Marked));
        
        PdfDictionary pages = root.getAsDictionary(PdfName.Pages);
        ShowObjects.showNumber(pages.getAsNumber(PdfName.Count));
        
        ShowObjects.showName(root.getAsName(PdfName.Type));
        
        PdfDictionary info = trailer.getAsDictionary(PdfName.Info);
        ShowObjects.showString(info.getAsString(PdfName.CreationDate));
        PdfArray id = trailer.getAsArray(PdfName.ID);
        ShowObjects.showString(id.getAsString(0));
        
        ShowObjects.showArray(id);
        
        ShowObjects.showDictionary(info);
        
        PdfDictionary page = pages.getAsArray(PdfName.Kids).getAsDictionary(0);
        ShowObjects.showStream(page.getAsStream(PdfName.Contents));
        
        ShowObjects.showObject(page.get(PdfName.Contents, false));
    }
}
