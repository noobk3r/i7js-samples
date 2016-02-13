///**
// * This example was written on the basis of Bruno Lowagie's answer to a question by a customer.
// */
//package com.itextpdf.samples.sandbox.itext6presentation;
//
//import com.itextpdf.core.pdf.PdfDictionary;
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfName;
//import com.itextpdf.core.pdf.PdfObject;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfWriter;
//
//import java.io.IOException;
//
//// This example shows how handy is to use PdfDocument.
//// Notice that the logic of unembedTTF(PdfDictionary) is not so important in the context of the example.
//public class UnembedFont {
//    public static final String DEST = "./samples/target/test/resources/sandbox/itext6presentation/unembed_font.pdf";
//    public static final String SRC = "./samples/target/test/resources/sandbox/fonts/withSerifFont.pdf";
//
//    public static void main(String[] args) throws IOException {
//        new UnembedFont().manipulatePdf();
//    }
//
//    protected void manipulatePdf() throws IOException {
//        // Use common PdfDocument logic instead of PdfReader manipulation
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
//
//        // Loop over all objects
//        PdfObject obj;
//        for (int i = 1; i < pdfDoc.getNumberOfPdfObjects(); i++) {
//            obj = pdfDoc.getPdfObject(i);
//            // Skip all objects that aren't a dictionary
//            if (obj == null || !obj.isDictionary())
//                continue;
//            // Process all dictionaries
//            unembedTTF((PdfDictionary) obj);
//        }
//
//        // The unused objects will be removed automatically
//
//        pdfDoc.close();
//    }
//
//    /**
//     * Processes a dictionary.
//     */
//    public void unembedTTF(PdfDictionary dict) {
//        // we ignore all dictionaries that aren't font dictionaries
//        if (!PdfName.Font.equals(dict.getAsName(PdfName.Type))) {
//            return;
//        }
//        // we only remove TTF fonts
//        if (dict.getAsDictionary(PdfName.FontFile2) != null) {
//            return;
//        }
//        // check if a subset was used (in which case we remove the prefix)
//        PdfName baseFont = dict.getAsName(PdfName.BaseFont);
//        if (baseFont.getValue().getBytes()[6] == '+') {
//            baseFont = new PdfName(baseFont.getValue().substring(7));
//            dict.put(PdfName.BaseFont, baseFont);
//        }
//        // we check if there's a font descriptor
//        PdfDictionary fontDescriptor = dict.getAsDictionary(PdfName.FontDescriptor);
//        if (fontDescriptor == null)
//            return;
//        // is there is, we replace the fontname and remove the font file
//        fontDescriptor.put(PdfName.FontName, baseFont);
//        fontDescriptor.remove(PdfName.FontFile2);
//    }
//}