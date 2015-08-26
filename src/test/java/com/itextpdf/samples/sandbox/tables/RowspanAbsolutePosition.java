package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

public class RowspanAbsolutePosition extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/rowspan_absolute_position.pdf";
    public static final String IMG = "./src/test/resources/sandbox/tables/berlin2013.jpg";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RowspanAbsolutePosition().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table1 = new Table(new float[]{15, 20, 20});
        table1.setWidth(555);
        Cell cell = new Cell(1, 2).add(new Paragraph("{Month}"));
        cell.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        Image img = new Image(ImageFactory.getImage(IMG));
        img.scaleToFit(555f * 20f / 55f, 10000);
        Cell cell2 = new Cell(2, 1).add(img.setAutoScaling(true));
        Cell cell3 = new Cell(1, 2).add(new Paragraph("Mr Fname Lname"));
        cell3.setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
        table1.addCell(cell);
        table1.addCell(cell2);
        table1.addCell(cell3);
        // TODO Implement writeSelectedRows
        // table1.writeSelectedRows(0, -1, 20 , 820, writer.getDirectContent());
        doc.add(table1);

        doc.close();
    }
}
