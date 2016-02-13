/**
* Example written on the basis of Bruno Lowagie's answer to:
* http://stackoverflow.com/questions/28590487/adding-table-to-existing-pdf-on-the-same-page-itext
*/

package com.itextpdf.samples.sandbox.itext6presentation;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddExtraTable {
    public static String SRC = "./samples/src/test/resources/sandbox/acroforms/form.pdf";
    public static String DEST = "./samples/target/test/resources/sandbox/itext6presentation/add_extra_table.pdf";

    public static void main(String[] args) throws Exception {
        new AddExtraTable().manipulatePdf();
    }

    protected void manipulatePdf() throws IOException {
        PdfReader reader = new PdfReader(new FileInputStream(SRC));
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("Name").setValue("Jeniffer");
        fields.get("Company").setValue("iText's next customer");
        fields.get("Country").setValue("No Man's Land");
        form.flattenFields();

        // Notice that standard width percentage in itext6 is 100% instead of 80%
        Table table = new Table(new float[]{1, 15});
        table.addHeaderCell("#");
        table.addHeaderCell("description");

        for (int i = 1; i <= 150; i++) {
            table.addCell(String.valueOf(i));
            table.addCell("test " + i);
        }

        // Compare with ColumnText and look how pretty the renderers usage is.
        doc.setRenderer(new DocumentRenderer(doc) {
            @Override
            protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
                LayoutArea area = super.updateCurrentArea(overflowResult);
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
