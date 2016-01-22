package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_13_15_InspectForm extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter13/Listing_13_15_InspectForm.pdf";
    public static final String RESULTTXT = "./target/test/resources/book/part4/chapter13/Listing_13_15_InspectForm_fieldflags.txt";
    public static final String SUBSCRIBE = "./src/test/resources/book/part2/chapter08/cmp_Listing_08_15_Subscribe.pdf";

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_15_InspectForm().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(RESULTTXT));
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SUBSCRIBE));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        PdfDictionary dict;
        int flags;
        PdfFormField item;
        for (String key : fields.keySet()) {
            out.write(key);
            item = fields.get(key);
            // TODO No getMerged
            dict = item.getPdfObject();
            flags = dict.getAsNumber(PdfName.Ff).getIntValue();
            if ((flags & PdfFormField.FF_PASSWORD) > 0)
                out.write(" -> password");
            if ((flags & PdfFormField.FF_MULTILINE) > 0)
                out.write(" -> multiline");
            out.write('\n');
        }
        out.flush();
        out.close();
        pdfDoc.close();
    }
}
