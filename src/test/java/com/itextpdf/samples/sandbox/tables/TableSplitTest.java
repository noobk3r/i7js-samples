package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.SolidBorder;
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
public class TableSplitTest extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/tables_split_test.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableSplitTest().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(595, 842).setMargins(15, 15, 55, 35));

        String[] header = new String[]{"Header1", "Header2", "Header3",
                "Header4", "Header5"};
        String[] content = new String[]{"column 1", "column 2",
                "some Text in column 3", "Test data ", "column 5"};
        Table table = new Table(new float[]{3, 2, 4, 3, 2});
        table.setWidthPercent(98);
        table.setMarginTop(15);
        // TODO Implement setSplitLate(boolean)
        //table.setSplitLate(false);
        for (String columnHeader : header) {
            Cell headerCell = new Cell().add(new Paragraph(columnHeader).setFont(new PdfType1Font(pdfDoc,
                    (Type1Font) FontFactory.createFont(FontConstants.HELVETICA_BOLD, PdfEncodings.WINANSI)))
                    .setFontSize(10));
            headerCell.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
            headerCell.setVerticalAlignment(Property.VerticalAlignment.MIDDLE);
            headerCell.setBorder(new SolidBorder(Color.LIGHT_GRAY, 1));
            headerCell.setPadding(8);
            table.addHeaderCell(headerCell);
        }
        for (int i = 0; i < 15; i++) {
            int j = 0;
            for (String text : content) {
                if (i == 13 && j == 3) {
                    text = "Test data \n Test data \n Test data";
                }
                j++;
                Cell cell = new Cell().add(new Paragraph(text).setFont(new PdfType1Font(pdfDoc,
                        (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)))
                        .setFontSize(10));
                cell.setBorder(new SolidBorder(Color.LIGHT_GRAY, 1));
                cell.setPaddingLeft(5);
                cell.setPaddingTop(5);
                cell.setPaddingRight(5);
                cell.setPaddingBottom(5);
                table.addCell(cell);
            }
        }
        doc.add(table);
        doc.add(new Paragraph("\n"));
        // TODO Implement LineSeparator
        //LineSeparator separator = new LineSeparator();
        //separator.setPercentage(98);
        //separator.setLineColor(BaseColor.LIGHT_GRAY);
        //Chunk linebreak = new Chunk(separator);
        //doc.add(linebreak);
        for (int k = 0; k < 5; k++) {
            Paragraph info = new Paragraph("Some title").setFont(new PdfType1Font(pdfDoc,
                    (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)))
                    .setFontSize(10);
            info.setMarginTop(12f);
            doc.add(info);
            table = new Table(new float[]{3, 2, 4, 3, 2});
            table.setWidthPercent(98);
            table.setMarginTop(15);
            // TODO Implement setSplitLate(boolean)
            //table.setSplitLate(false);
            for (String columnHeader : header) {
                Cell headerCell = new Cell().add(new Paragraph(columnHeader).setFont(new PdfType1Font(pdfDoc,
                        (Type1Font) FontFactory.createFont(FontConstants.HELVETICA_BOLD, PdfEncodings.WINANSI)))
                        .setFontSize(10));
                headerCell.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
                headerCell.setVerticalAlignment(Property.VerticalAlignment.MIDDLE);
                headerCell.setBorder(new SolidBorder(Color.LIGHT_GRAY, 1));
                headerCell.setPaddingLeft(8);
                headerCell.setPaddingTop(8);
                headerCell.setPaddingRight(8);
                headerCell.setPaddingBottom(8);
                table.addHeaderCell(headerCell);
            }
            for (String text : content) {
                Cell cell = new Cell().add(new Paragraph(text).setFont(new PdfType1Font(pdfDoc,
                        (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)))
                        .setFontSize(10));
                cell.setBorder(new SolidBorder(Color.LIGHT_GRAY, 1));
                cell.setPaddingLeft(5);
                cell.setPaddingTop(5);
                cell.setPaddingRight(5);
                cell.setPaddingBottom(5);
                table.addCell(cell);
            }
            doc.add(table);
            //separator = new LineSeparator();
            //separator.setPercentage(98);
            //separator.setLineColor(BaseColor.LIGHT_GRAY);
            //linebreak = new Chunk(separator);
            //document.add(linebreak);
        }

        doc.close();
    }
}