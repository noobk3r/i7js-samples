/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21607286/unicode-characters-in-document-info-dictionary-keys
 *
 * A user wants to update a Document Info Dictionary (DID)
 * introducing a custom key with a Unicode character.
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfDocumentInfo;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ChangeInfoDictionary extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_info_dictionary.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLongTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfDocumentInfo info = pdfDoc.getInfo();
        Map<String, String> newInfo = new HashMap<>();
        newInfo.put("Special Character: \u00e4", "\u00e4");
        StringBuilder buf = new StringBuilder();
        buf.append((char) 0xc3);
        buf.append((char) 0xa4);
        newInfo.put(buf.toString(), "\u00e4");
        info.setMoreInfo(newInfo);
        pdfDoc.close();
    }
}
