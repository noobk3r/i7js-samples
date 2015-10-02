package com.itextpdf.samples.book.chapter08;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_08_15_Subscribe extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_08_15_Subscribe/Listing_08_15_Subscribe.pdf";
    public static final String SRC = "./target/test/resources/Listing_08_15_Subscribe/subscribe.pdf";

    private String name = "Bruno Lowagie";
    private String login = "blowagie";

    public static void main(String[] args) throws Exception {
        Listing_08_15_Subscribe subscribe = new Listing_08_15_Subscribe();
        subscribe.createPdf(SRC);
        subscribe.name = "Bruno Lowagie";
        subscribe.login = "blowagie";
        subscribe.manipulatePdf(String.format(DEST, 1));
    }

    protected void manipulatePdf(String dest) throws Exception {
        createPdf(SRC);

        FileOutputStream fos = new FileOutputStream(dest);
        PdfReader reader = new PdfReader(SRC);
        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        form.removeField("personal.password");
        form.getField("personal.name").setValue(name);
        form.getField("personal.loginname").setValue(login);
        form.renameField("personal.reason", "personal.motivation");
        form.getField("personal.loginname").setReadOnly(true);
        form.partialFormFlattening("personal.name");
        form.flatFields();

        //Close document
        pdfDoc.close();
    }

    protected void createPdf(String filename) throws IOException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(filename));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        PdfFormField personal = PdfFormField.createEmptyField(pdfDoc);
        personal.setFieldName("personal");
        Table table = new Table(3);
        Cell cell;

        table.addCell(new Cell().add(new Paragraph("Your name:")));
        cell = new Cell(1, 2);
        PdfTextFormField field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "", "name");
        personal.addKid(field);
        cell.setNextRenderer(createCellRenderer(field, 1, cell));
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("Login:")));
        cell = new Cell();
        field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "", "loginname");
        personal.addKid(field);
        cell.setNextRenderer(createCellRenderer(field, 1, cell));
        table.addCell(cell);
        cell = new Cell();
        field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "", "password");
        field.setPassword(true);
        personal.addKid(field);
        cell.setNextRenderer(createCellRenderer(field, 1, cell));
        table.addCell(cell);
        table.addCell(new Cell().add(new Paragraph("Your motivation:")));
        cell = new Cell(1, 2);
        cell.setHeight(60);
        field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "", "reason");
        field.setMultiline(true);
        personal.addKid(field);
        cell.setNextRenderer(createCellRenderer(field, 1, cell));;
        table.addCell(cell);

        doc.add(table);

        PdfAcroForm.getAcroForm(pdfDoc, true).addField(personal);

        doc.close();
    }

    private CellRenderer createCellRenderer(PdfFormField field, float padding, Cell cell) {
        return new CellRenderer(cell) {
            PdfFormField field;
            float padding;

            public CellRenderer setParameters(PdfFormField field, float padding) {
                this.field = field;
                this.padding = padding;

                return this;
            }

            @Override
            public void draw(PdfDocument doc, PdfCanvas canvas) {
                PdfWidgetAnnotation widget = field.getWidgets().get(0);
                Rectangle rect = widget.getRectangle().toRectangle();
                Rectangle bBox = getOccupiedAreaBBox();
                rect.setBbox(bBox.getLeft() + padding, bBox.getBottom() + padding, bBox.getRight() - padding, bBox.getTop() - padding);
                widget.setRectangle(new PdfArray(rect));
                widget.setHighlightMode(PdfName.I);
                super.draw(doc, canvas);
            }
        }.setParameters(field, padding);
    }
}
