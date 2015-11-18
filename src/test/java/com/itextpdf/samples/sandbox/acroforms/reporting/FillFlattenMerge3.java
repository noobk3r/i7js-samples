package com.itextpdf.samples.sandbox.acroforms.reporting;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FillFlattenMerge3 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/reporting/state.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/reporting/fill_flatten_merge3.pdf";
    public static final String DATA = "./src/test/resources/sandbox/acroforms/reporting/united_states.csv";
    public static final String[] FIELDS = {
            "name", "abbr", "capital", "city", "population", "surface", "timezone1", "timezone2", "dst"
    };


    protected Map<String, Rectangle> positions;

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillFlattenMerge3().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        PdfFont font = new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI));


        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        positions = new HashMap<String, Rectangle>();
        Rectangle rectangle;
        Map<String, PdfFormField> fields = form.getFormFields();
        for (String name : fields.keySet()) {
            rectangle = fields.get(name).getWidgets().get(0).getRectangle().toRectangle();
            positions.put(name, rectangle);
        }

        // TODO Implement PdfImportedPage
        // writer.setPageEvent(new Background(writer.getImportedPage(reader, 1)));
        Document doc = new Document(pdfDoc);
        StringTokenizer tokenizer;
        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            int i = 0;
            tokenizer = new StringTokenizer(line, ";");
            while (tokenizer.hasMoreTokens()) {
                process(doc, FIELDS[i++], tokenizer.nextToken(), font);
            }
            pdfDoc.addNewPage();
        }
        br.close();
        pdfDoc.close();
    }

    protected void process(Document doc, String name, String value, PdfFont font) {
        Rectangle rect = positions.get(name);
        Paragraph p = new Paragraph(value).setFont(font).setFontSize(10);
        doc.showTextAligned(p, rect.getLeft() + 2, rect.getBottom() + 2, doc.getPdfDocument().getNumOfPages(),
                Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
    }

//    public class Background extends PdfPageEventHelper {
//
//        PdfImportedPage background;
//
//        public Background(PdfImportedPage background) {
//            this.background = background;
//        }
//
//        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfContentByte cb = writer.getDirectContentUnder();
//            cb.addTemplate(background, 0, 0);
//            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase("page " + writer.getPageNumber()), 550, 800, 0);
//        }
//    }

}
