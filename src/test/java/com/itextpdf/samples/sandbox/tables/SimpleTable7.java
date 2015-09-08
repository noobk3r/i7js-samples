package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class SimpleTable7 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/simple_table7.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable7().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        PdfFont titleFont = new PdfType1Font(pdfDoc, (Type1Font) FontFactory.createFont(FontConstants.COURIER_BOLD, PdfEncodings.WINANSI));
        Paragraph docTitle = new Paragraph("UCSC Direct - Direct Payment Form");
        docTitle.setFont(titleFont);
        docTitle.setFontSize(11);
        docTitle.setFontColor(Color.BLACK);
        doc.add(docTitle);

        PdfFont subtitleFont = new PdfType1Font(pdfDoc, (Type1Font) FontFactory.createFont(FontConstants.TIMES_ROMAN, PdfEncodings.WINANSI));
        Paragraph subTitle = new Paragraph("(not to be used for reimbursement of services)");
        subTitle.setFont(subtitleFont);
        doc.add(subTitle);

        PdfFont importantNoticeFont = new PdfType1Font(pdfDoc, (Type1Font) FontFactory.createFont(FontConstants.COURIER, PdfEncodings.WINANSI));
        Paragraph importantNotice = new Paragraph("Important: Form must be filled out in Adobe Reader or Acrobat Professional 8.1 or above. To save completed forms, Acrobat Professional is required. For technical and accessibility assistance, contact the Campus Controller's Office.");
        importantNotice.setFont(importantNoticeFont);
        importantNotice.setFontSize(9);
        importantNotice.setFontColor(Color.RED);
        doc.add(importantNotice);

        Table table = new Table(10);
        Cell cell = new Cell(1, 3).add(docTitle);
        cell.setBorder(Border.NO_BORDER);
        cell.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        table.addCell(cell);

        Cell cellCaveat = new Cell(1, 2).add(subTitle);
        cellCaveat.setBorder(null);
        table.addCell(cellCaveat);

        Cell cellImportantNote = new Cell(1, 5).add(importantNotice);
        cellImportantNote.setBorder(null);
        table.addCell(cellImportantNote);

        doc.add(table);
        doc.add(new Paragraph("This is the same table, created differently").setFont(subtitleFont));

        table = new Table(new float[]{3, 2, 5});
        table.setWidth(0);
        // TODO Implement setColSpan in already existed cell
        // cell.setColspan(1);
        table.addCell(new Cell().add(docTitle).setBorder(null));
        table.addCell(new Cell().add(subTitle).setBorder(null));
        table.addCell(new Cell().add(importantNotice).setBorder(null));
        doc.add(table);

        doc.close();
    }
}
