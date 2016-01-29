/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24506830/can-we-use-text-extraction-strategy-after-applying-location-extraction-strategy
 */
package com.itextpdf.samples.sandbox.parse;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ParseCustom {
    public static final String SRC = "./src/test/resources/sandbox/parse/nameddestinations.pdf";

    @BeforeClass
    public static void beforeClass() throws IOException {
        File file = new File(SRC);
        file.getParentFile().mkdirs();
        new ParseCustom().manipulatePdf();
    }

    @Test
    public void manipulatePdf() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        Rectangle rect = new Rectangle(36, 750, 559, 806);
        // RenderFilter regionFilter = new RegionTextRenderFilter(rect);
        // FontRenderFilter fontFilter = new FontRenderFilter();
        // TextExtractionStrategy strategy = new FilteredTextRenderListener(
        //         new LocationTextExtractionStrategy(), regionFilter, fontFilter);
        // System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
        pdfDoc.close();
    }

    // TODO There is no predefined extraction strategies
//    class FontRenderFilter extends RenderFilter {
//        public boolean allowText(TextRenderInfo renderInfo) {
//            String font = renderInfo.getFont().getPostscriptFontName();
//            return font.endsWith("Bold") || font.endsWith("Oblique");
//        }
//    }
}
