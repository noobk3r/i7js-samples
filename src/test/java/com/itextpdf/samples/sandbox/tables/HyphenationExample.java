package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class HyphenationExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/hyphenation_example.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new HyphenationExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(595, 842).setMargins(0, 0, 0, 0));
        // TODO Implement hyphenation
        // Hyphenator h = new Hyphenator("de", "DE", 2, 2);
        // Hyphenation s = h.hyphenate("Leistungsscheinziffer");
        // System.out.println(s);
        Table table = new Table(1);
        table.setWidthPercent(10);
        Paragraph chunk = new Paragraph("Leistungsscheinziffer");
        //chunk.setHyphenation(new HyphenationAuto("de", "DE", 2, 2));
        table.addCell(new Cell().add(chunk));
        Paragraph phrase = new Paragraph();
        //phrase.setHyphenation(new HyphenationAuto("de", "DE", 2, 2));
        phrase.add("Leistungsscheinziffer");
        table.addCell(new Cell().add(phrase));
        doc.add(table);

        doc.close();
    }
}
