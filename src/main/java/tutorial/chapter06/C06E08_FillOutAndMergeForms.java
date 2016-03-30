package tutorial.chapter06;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.xmp.XMPException;

import java.io.*;
import java.util.Map;
import java.util.StringTokenizer;

public class C06E08_FillOutAndMergeForms {
    public static final String DEST = "results/chapter06/fill_out_and_merge_forms.pdf";
    public static final String SRC = "src/main/resources/pdf/state.pdf";
    public static final String DATA = "src/main/resources/data/united_states.csv";

    public static void main(String args[]) throws IOException, XMPException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C06E08_FillOutAndMergeForms().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, XMPException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest));

        BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA));
        String line;
        boolean headerLine = true;
        while ((line = bufferedReader.readLine()) != null) {
            if (headerLine) {
                headerLine = false;
                continue;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument sourcePdfDocument = new PdfDocument(new PdfReader(SRC), new PdfWriter(baos));

            //Read fields
            PdfAcroForm form = PdfAcroForm.getAcroForm(sourcePdfDocument, true);
            StringTokenizer tokenizer = new StringTokenizer(line, ";");
            Map<String, PdfFormField> fields = form.getFormFields();

            //Fill out fields
            fields.get("name").setValue(tokenizer.nextToken());
            fields.get("abbr").setValue(tokenizer.nextToken());
            fields.get("capital").setValue(tokenizer.nextToken());
            fields.get("city").setValue(tokenizer.nextToken());
            fields.get("population").setValue(tokenizer.nextToken());
            fields.get("surface").setValue(tokenizer.nextToken());
            fields.get("timezone1").setValue(tokenizer.nextToken());
            fields.get("timezone2").setValue(tokenizer.nextToken());
            fields.get("dst").setValue(tokenizer.nextToken());

            sourcePdfDocument.close();
            sourcePdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));

            //Copy pages
            sourcePdfDocument.copyPagesTo(1, sourcePdfDocument.getNumberOfPages(), pdfDocument, new PdfPageFormCopier());
            sourcePdfDocument.close();
        }

        bufferedReader.close();
        pdfDocument.close();
    }
}
