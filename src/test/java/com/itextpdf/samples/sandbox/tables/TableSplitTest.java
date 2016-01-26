package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

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
        Document doc = new Document(pdfDoc, new PageSize(595, 842));
        doc.setMargins(55, 15, 35, 15);

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
            Cell headerCell = new Cell().add(new Paragraph(columnHeader).setFont(
                    PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD))
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
                Cell cell = new Cell().add(new Paragraph(text).setFont(
                        PdfFontFactory.createStandardFont(FontConstants.HELVETICA))
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
            Paragraph info = new Paragraph("Some title").setFont(
                    PdfFontFactory.createStandardFont(FontConstants.HELVETICA))
                    .setFontSize(10);
            info.setMarginTop(12f);
            doc.add(info);
            table = new Table(new float[]{3, 2, 4, 3, 2});
            table.setWidthPercent(98);
            table.setMarginTop(15);
            // TODO Implement setSplitLate(boolean)
            //table.setSplitLate(false);
            for (String columnHeader : header) {
                Cell headerCell = new Cell().add(new Paragraph(columnHeader).setFont(
                        PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD))
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
                Cell cell = new Cell().add(new Paragraph(text).setFont(
                        PdfFontFactory.createStandardFont(FontConstants.HELVETICA))
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