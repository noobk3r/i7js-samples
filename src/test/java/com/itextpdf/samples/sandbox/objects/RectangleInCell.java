/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21625435/c-sharp-add-rectangle-into-pdfpcell-itextsharp
 *
 * Adding a Rectangle object inside a table cell.
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class RectangleInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/rectangle_in_cell.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new OrdinalNumbers().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("Option 1:"));
        Table table = new Table(3);
        table.addCell(new Cell().add(new Paragraph("A rectangle:)")));
        // TODO There is no PdfTemplate
//        PdfTemplate template = writer.getDirectContent().createTemplate(120, 80);
//        template.setColorFill(BaseColor.RED);
//        template.rectangle(0, 0, 120, 80);
//        template.fill();
//        writer.releaseTemplate(template);
//        table.addCell(Image.getInstance(template));
        table.addCell(new Cell().add(
                new Paragraph("The rectangle is scaled to fit inside the cell, you see a padding.")));
        doc.add(table);
        doc.add(new Paragraph("Option 2:"));
        table = new Table(3);
        table.addCell(new Cell().add(new Paragraph("A rectangle:")));
        Cell cell = null; //
        // new Cell().add(ImageFactory.getImage(template));
        //table.addCell(cell);
        table.addCell(new Cell().add(
                new Paragraph("The rectangle keeps its original size, but can overlap other cells in the same row.")));
        doc.add(table);
        doc.add(new Paragraph("Option 3:"));
        table = new Table(3);
        table.addCell(new Cell().add(new Paragraph("A rectangle:")));
        //cell = new Cell(ImageFactory.getImage(template), true);
        table.addCell(cell);
        table.addCell(new Cell().add(
                new Paragraph("The rectangle is scaled to fit inside the cell, no padding.")));
        doc.add(table);
        PdfCanvas cb = new PdfCanvas(pdfDoc.getFirstPage());
        cb.moveTo(228, 810);
        cb.lineTo(338, 810);
        cb.stroke();

        doc.close();
    }
}
