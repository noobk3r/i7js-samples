/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28590487/adding-table-to-existing-pdf-on-the-same-page-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class AddExtraTable extends GenericTest {
    public static String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static String DEST = "./target/test/resources/sandbox/acroforms/add_extra_table.pdf";

    public static void main(String[] args) throws Exception {
        new AddExtraTable().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(new FileInputStream(SRC));
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Rectangle pagesize = pdfDoc.getPage(1).getPageSize();
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("Name").setValue("Jeniffer");
        fields.get("Company").setValue("iText's next customer");
        fields.get("Country").setValue("No Man's Land");
        form.flatFields();

        Table table = new Table(new float[]{1, 15});
        table.setWidth(0);
        table.addHeaderCell(new Cell().add(new Paragraph("#")));
        table.addHeaderCell(new Cell().add(new Paragraph("description")));

        for (int i = 1; i <= 150; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
            table.addCell(new Cell().add(new Paragraph("test " + i)));
        }
        // TODO Implement adding in special area like ColumnText could
        /* There is no ColumnText in itext6 */
        // Implement it other way
        // ColumnText column = new ColumnText(stamper.getOverContent(1));
        //Rectangle rectPage1 = new Rectangle(36, 36, 559, 540);
        //column.setSimpleColumn(rectPage1);
        //column.addElement(table);
        //table.setWidth(559-36);
        // TODO Problems with margins
        doc.add(table);

        // int pagecount = 1;
        // Rectangle rectPage2 = new Rectangle(36, 36, 559, 806);
        // int status = column.go();
        // while (ColumnText.hasMoreText(status)) {
        //    status = triggerNewPage(stamper, pagesize, column, rectPage2, ++pagecount);
        // }
        pdfDoc.close();
    }

    /* There is no need in such method because of ColumnText's absence in itext6 */
//    public int triggerNewPage(PdfStamper stamper, Rectangle pagesize,
// ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
//        stamper.insertPage(pagecount, pagesize);
//        PdfContentByte canvas = stamper.getOverContent(pagecount);
//        column.setCanvas(canvas);
//        column.setSimpleColumn(rect);
//        return column.go();
//    }
}
