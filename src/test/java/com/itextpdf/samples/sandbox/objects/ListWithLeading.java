package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.ListItem;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Category(SampleTest.class)
public class ListWithLeading extends GenericTest {

    public static final String DEST = "./target/test/resources/sandbox/objects/list_with_leading.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ListWithLeading().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        PdfFont font = new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""));
        List list1 = new List().
                setSymbolIndent(12).
                setListSymbol("\u2022").
                setFont(font);
        list1.add(new ListItem("Value 1")).
                add(new ListItem("Value 2")).
                add(new ListItem("Value 3"));
        doc.add(list1);

        List list2 = new List().
                setSymbolIndent(12).
                setListSymbol("\u2022");
        list2.add((ListItem) new ListItem().add(new Paragraph("Value 1").setFixedLeading(30).setMargins(0, 0, 0, 0))).
                add((ListItem) new ListItem().add(new Paragraph("Value 2").setFixedLeading(30).setMargins(0, 0, 0, 0))).
                add((ListItem) new ListItem().add(new Paragraph("Value 3").setFixedLeading(30).setMargins(0, 0, 0, 0)));
        list2.setMarginLeft(60);
        doc.add(list2);

        doc.close();
    }

}
