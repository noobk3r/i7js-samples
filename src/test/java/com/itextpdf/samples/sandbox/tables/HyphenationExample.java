package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.hyphenation.Hyphenation;
import com.itextpdf.model.hyphenation.HyphenationConfig;
import com.itextpdf.model.hyphenation.Hyphenator;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

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
        Hyphenation s = Hyphenator.hyphenate("de", "DE", "Leistungsscheinziffer", 2, 2);
        System.out.println(s);

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.clone());
        doc.setMargins(0, 0, 0, 0);
        Table table = new Table(1);
        table.setWidthPercent(10);
        Text chunk = new Text("Leistungsscheinziffer");
        chunk.setHyphenation(new HyphenationConfig("de", "DE", 2, 2));
        table.addCell(new Cell().add(new Paragraph(chunk)));
        Paragraph phrase = new Paragraph();
        phrase.setHyphenation(new HyphenationConfig("de", "DE", 2, 2));
        phrase.add("Leistungsscheinziffer");
        table.addCell(new Cell().add(phrase));

        // soft hyphens
        table.addCell(new Cell().add(new Paragraph("Le\u00adistun\u00ADgssch\u00ADeinziffe\u00ADr").setHyphenation(new HyphenationConfig(3, 2))));

        doc.add(table);

        doc.close();
    }
}
