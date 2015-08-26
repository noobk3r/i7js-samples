package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToTable extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/array_to_table.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ArrayToTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(8);
        // TODO Implement setWidthPercentage(float) method
        // table.setWidthPercentage(100);
        List<List<String>> dataset = getData();
        for (List<String> record : dataset) {
            for (String field : record) {
                table.addCell(new Cell().add(new Paragraph(field)));
            }
        }
        doc.add(table);

        doc.close();
    }

    public List<List<String>> getData() {
        List<List<String>> data = new ArrayList<List<String>>();
        String[] tableTitleList = {" Title", " (Re)set", " Obs", " Mean", " Std.Dev", " Min", " Max", "Unit"};
        data.add(Arrays.asList(tableTitleList));
        for (int i = 0; i < 10; ) {
            List<String> dataLine = new ArrayList<String>();
            i++;
            for (int j = 0; j < tableTitleList.length; j++) {
                dataLine.add(tableTitleList[j] + " " + i);
            }
            data.add(dataLine);
        }
        return data;
    }
}
