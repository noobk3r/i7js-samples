/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/26853894/continue-field-output-on-second-page-with-itextsharp
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.FileInputStream;
import java.io.FileOutputStream;

// TODO problems with src-file to open. Temporary change src
@Ignore
@Category(SampleTest.class)
public class AddExtraPage extends GenericTest {
    public static String SRC = "./src/test/resources/sandbox/acroforms/subscribe.pdf";
    public static String DEST = "./target/test/resources/sandbox/acroforms/add_extra_page.pdf";

    public static void main(String[] args) throws Exception {
        new AddExtraPage().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(new FileInputStream(SRC));
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Rectangle pageSize = pdfDoc.getDefaultPageSize();
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        Paragraph p = new Paragraph();
        p.add(new Text("Hello "));
        // TODO Implement usage of Font.BOLD
        p.add(new Text("World").setFont(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI))).setFontSize(12));
        PdfArray rectArray = form.getField("body").getWidgets().get(0).getRectangle();
        // TODO Implement PdfImportedPage or summat
        /* We do not have ColumnText in itex6 */
        // PdfImportedPage newPage = null;
        // ColumnText column = new ColumnText(stamper.getOverContent(1));
        //column.setSimpleColumn(rect);
        //int pagecount = 1;
        // int status;
        for (int i = 1; i < 101; i++) {
            doc.add(new Paragraph("Hello " + i));
            doc.add(p);
            // status = column.go();
            // if (ColumnText.hasMoreText(status)) {
            //    newPage = loadPage(newPage, reader, stamper);
            //    triggerNewPage(stamper, pagesize, newPage, column, rect, ++pagecount);
            //}
        }

        form.flatFields();
        pdfDoc.close();
    }

    // TODO Implement PdfImportedPage or summat
    //public PdfImportedPage loadPage(PdfImportedPage page, PdfReader reader, PdfStamper stamper) {
    //    if (page == null) {
    //        return stamper.getImportedPage(reader, 1);
    //    }
    //    return page;
    //}
    /* There is no need ins such method: we do not have ColumnText in itex6 */
    //public void triggerNewPage(PdfStamper stamper, Rectangle pagesize,
    // PdfImportedPage page, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
    //    stamper.insertPage(pagecount, pagesize);
    //    PdfContentByte canvas = stamper.getOverContent(pagecount);
    //    canvas.addTemplate(page, 0, 0);
    //    column.setCanvas(canvas);
    //    column.setSimpleColumn(rect);
    //    column.go();
    //}
}
