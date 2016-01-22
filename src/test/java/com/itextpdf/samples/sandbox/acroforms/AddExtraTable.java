/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28590487/adding-table-to-existing-pdf-on-the-same-page-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.junit.experimental.categories.Category;

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

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("Name").setValue("Jeniffer");
        fields.get("Company").setValue("iText's next customer");
        fields.get("Country").setValue("No Man's Land");
        form.flatFields();

        Table table = new Table(new float[]{1, 15});
        table.setWidthPercent(80);
        table.addHeaderCell(new Cell().add(new Paragraph("#")));
        table.addHeaderCell(new Cell().add(new Paragraph("description")));

        for (int i = 1; i <= 150; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
            table.addCell(new Cell().add(new Paragraph("test " + i)));
        }

        doc.setRenderer(new DocumentRenderer(doc) {
            @Override
            protected LayoutArea getNextArea(LayoutResult overflowResult) {
                LayoutArea area = super.getNextArea(overflowResult);
                if (area.getPageNumber() == 1) {
                    area.getBBox().decreaseHeight(266);
                }
                return area;
            }
        });

        doc.add(table);

        doc.close();
    }
}
