package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.PdfRuntimeException;
import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IndentationOptions extends GenericTest {

    public static final String DEST = "./target/test/resources/sandbox/objects/indentation_options.pdf";
    public static final String LABEL = "A list of stuff: ";
    public static final String CONTENT = "test A, test B, coconut, coconut, watermelons, apple, oranges, many more " +
            "fruites, carshow, monstertrucks thing, everything is startting on the " +
            "same point in the line now";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new IndentationOptions().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        List list = new List().
            setListSymbol(LABEL).
            add(CONTENT);
        doc.add(list);

        PdfFont font = new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""));
        Paragraph p = new Paragraph(LABEL + CONTENT).setFont(font);
        float indentation = font.getWidth(LABEL) * 12 / 1000;
        p.setMarginLeft(indentation).
            setFirstLineIndent(-indentation);
        doc.add(p);

        // TODO uncomment this when table functionality is added.
//        Table table = new Table(2);
//        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//        table.setTotalWidth(new float[]{indentation + 4, 519 - indentation});
//        table.setLockedWidth(true);
//        table.addCell(LABEL);
//        table.addCell(CONTENT);
//        document.add(table);

        doc.close();
    }

}
