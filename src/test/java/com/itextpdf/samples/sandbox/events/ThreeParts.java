/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28502315/divide-page-in-2-parts-so-we-can-fill-each-with-different-source
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ThreeParts extends GenericTest {
    public static final String DEST
            = "./target/test/resources/sandbox/events/three_parts.pdf";
    public static final String TEMP1
            = "./target/test/resources/sandbox/events/three_partsLATIN.pdf";
    public static final String TEMP2
            = "./target/test/resources/sandbox/events/three_partsENGLISH.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ThreeParts().manipulatePdf(DEST);
    }

    public Paragraph createParagraph(String path) throws IOException {
        Paragraph p = new Paragraph();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), "UTF8"));
        StringBuffer buffer = new StringBuffer();
        String line = in.readLine();
        while (null != line) {
            buffer.append(line);
            line = in.readLine();
        }
        in.close();
        p.add(buffer.toString());
        return p;
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc;
        Document doc;
        Paragraph latin;
        Paragraph english;
        Paragraph french;

        // TODO Simplify this sample because the solution is pretty ugly
        pdfDoc = new PdfDocument(new PdfWriter(dest));
        doc = new Document(pdfDoc);
        doc.add(new AreaBreak());
        doc.close();

        int lastPageNumber = 0;
        int curLastPageNumber = 0;
        for (int section = 0; section < 3; section++) {
            latin = createParagraph(String.format("./src/test/resources/txt/liber1_%s_la.txt",
                    section + 1));
            english = createParagraph(String.format("./src/test/resources/txt/liber1_%s_en.txt",
                    section + 1));
            french = createParagraph(String.format("./src/test/resources/txt/liber1_%s_fr.txt",
                    section + 1));

            latin.setNextRenderer(new ThreePartsParagraphRenderer(latin, 2));
            english.setNextRenderer(new ThreePartsParagraphRenderer(english, 1));
            french.setNextRenderer(new ThreePartsParagraphRenderer(french, 0));

            pdfDoc = new PdfDocument(new PdfReader(dest), new PdfWriter(TEMP1));
            doc = new Document(pdfDoc);
            for (int i = 0; i < lastPageNumber; i++) {
                doc.add(new AreaBreak());
            }
            doc.add(latin);
            curLastPageNumber = Math.max(pdfDoc.getNumberOfPages(), curLastPageNumber);
            doc.close();

            pdfDoc = new PdfDocument(new PdfReader(TEMP1), new PdfWriter(TEMP2));
            doc = new Document(pdfDoc);
            for (int i = 0; i < lastPageNumber; i++) {
                doc.add(new AreaBreak());
            }
            doc.add(english);
            curLastPageNumber = Math.max(pdfDoc.getNumberOfPages(), curLastPageNumber);
            doc.close();

            pdfDoc = new PdfDocument(new PdfReader(TEMP2), new PdfWriter(dest));
            doc = new Document(pdfDoc);
            for (int i = 0; i < lastPageNumber; i++) {
                doc.add(new AreaBreak());
            }
            doc.add(french);
            curLastPageNumber = Math.max(pdfDoc.getNumberOfPages(), curLastPageNumber);
            doc.close();

            lastPageNumber = curLastPageNumber;
        }
        Files.delete(new File(TEMP1).toPath());
        Files.delete(new File(TEMP2).toPath());
    }


    class ThreePartsParagraphRenderer extends ParagraphRenderer {
        private int number;

        public ThreePartsParagraphRenderer(Paragraph modelElement, int number) {
            super(modelElement);
            this.number = number;
        }

        @Override
        public List<Rectangle> initElementAreas(LayoutArea area) {
            List<Rectangle> areas = new ArrayList<Rectangle>();
            Rectangle reviewedArea = area.getBBox().clone();
            reviewedArea.setHeight((842 - 72) / 3);
            reviewedArea.setY(36 + ((842 - 72) / 3) * number);
            areas.add(reviewedArea);
            return areas;
        }

        @Override
        protected ParagraphRenderer createSplitRenderer() {
            return new ThreePartsParagraphRenderer((Paragraph) modelElement, number);
        }

        @Override
        protected ParagraphRenderer createOverflowRenderer() {
            return new ThreePartsParagraphRenderer((Paragraph) modelElement, number);
        }
    }
}
