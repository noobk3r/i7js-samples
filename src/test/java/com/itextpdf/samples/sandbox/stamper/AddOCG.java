/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27956833/adding-comment-on-pdf-layer-created-using-itextsharp-in-adobe-reader
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Ignore
@Category(SampleTest.class)
public class AddOCG extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_ocg.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddOCG().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        List<PdfLayer> layers = pdfDoc.getCatalog().getOCProperties(true).getLayers();
        PdfWriter writer = pdfDoc.getWriter();
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(1));

        PdfLayer nested = new PdfLayer("Nested layers", pdfDoc);
        PdfLayer nested_1 = new PdfLayer("Nested layer 1", pdfDoc);
        PdfLayer nested_2 = new PdfLayer("Nested layer 2", pdfDoc);
        nested_2.setLocked(true);
        nested.addChild(nested_1);
        nested.addChild(nested_2);

        canvas.beginLayer(nested);
        canvas.beginText();
        canvas.moveText(50, 755);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("nested layers");
        canvas.endText();
        canvas.stroke();
        canvas.endLayer();

        canvas.beginLayer(nested_1);
        canvas.beginText();
        canvas.moveText(100, 800);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("nested layers 1");
        canvas.endText();
        canvas.stroke();
        canvas.endLayer();

        canvas.beginLayer(nested_2);
        canvas.beginText();
        canvas.moveText(100, 750);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("nested layers 2");
        canvas.endText();
        canvas.stroke();
        canvas.endLayer();

        PdfLayer group = PdfLayer.createTitle("Grouped layers", pdfDoc);
        PdfLayer layer1 = new PdfLayer("Group: layer 1", pdfDoc);
        PdfLayer layer2 = new PdfLayer("Group: layer 2", pdfDoc);
        group.addChild(layer1);
        group.addChild(layer2);
        canvas.beginLayer(layer1);
        canvas.beginText();
        canvas.moveText(50, 700);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("layer 1 in the group");
        canvas.endText();
        canvas.stroke();
        canvas.endLayer();

        canvas.beginLayer(layer2);
        canvas.beginText();
        canvas.moveText(50, 675);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("layer 2 in the group");
        canvas.endText();
        canvas.stroke();
        canvas.endLayer();

        PdfLayer radiogroup = PdfLayer.createTitle("Radio group", pdfDoc);
        PdfLayer radio1 = new PdfLayer("Radiogroup: layer 1", pdfDoc);
        radio1.setOn(true);
        PdfLayer radio2 = new PdfLayer("Radiogroup: layer 2", pdfDoc);
        radio2.setOn(false);
        PdfLayer radio3 = new PdfLayer("Radiogroup: layer 3", pdfDoc);
        radio3.setOn(false);
        radiogroup.addChild(radio1);
        radiogroup.addChild(radio2);
        radiogroup.addChild(radio3);
        ArrayList<PdfLayer> options = new ArrayList<PdfLayer>();
        options.add(radio1);
        options.add(radio2);
        options.add(radio3);
        PdfLayer.addOCGRadioGroup(pdfDoc, options);
        canvas.beginLayer(radio1);
        canvas.beginText();
        canvas.moveText(50, 600);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("option 1");
        canvas.endText();
        canvas.endLayer();
        canvas.beginLayer(radio2);
        canvas.beginText();
        canvas.moveText(50, 575);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("option 2");
        canvas.endText();
        canvas.endLayer();
        canvas.beginLayer(radio3);
        canvas.beginText();
        canvas.moveText(50, 550);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        canvas.showText("option 3");
        canvas.endText();
        canvas.endLayer();

        PdfLayer not_printed = new PdfLayer("not printed", pdfDoc);
        not_printed.setOnPanel(false);
        not_printed.setPrint("Print", false);
        canvas.beginLayer(not_printed);
        canvas.beginText();
        canvas.moveText(300, 700);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : CENTER here
        // TODO Implement rotation : 90 here
        canvas.showText("PRINT THIS PAGE");
        canvas.endText();
        canvas.endLayer();

        PdfLayer zoom = new PdfLayer("Zoom 0.75-1.25", pdfDoc);
        zoom.setOnPanel(false);
        zoom.setZoom(0.75f, 1.25f);
        canvas.beginLayer(zoom);
        canvas.beginText();
        canvas.moveText(30, 530);
        canvas.setFontAndSize(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI)), 12);
        // TODO Implement showTextAligned(properties) using canvas(in order to use layers) : LEFT here
        // TODO Implement rotation : 90 here
        canvas.showText("Only visible if the zoomfactor is between 75 and 125%");
        canvas.endText();
        canvas.endLayer();

        pdfDoc.close();
    }
}
