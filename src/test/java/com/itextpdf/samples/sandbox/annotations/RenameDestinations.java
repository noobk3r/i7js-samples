/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/20131610/renaming-named-destinations-in-pdf-files
 *
 * Searching for all the named destinations, with the purpose to rename them.
 * Change the destination for all GoTo actions from Link annotations on the first page.
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Category(SampleTest.class)
public class RenameDestinations extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/nameddestinations.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/rename_destinations.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RenameDestinations().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        Map<String, PdfString> renamed = new HashMap<>();
        PdfDictionary catalog = pdfDoc.getCatalog().getPdfObject();
        PdfDictionary names = catalog.getAsDictionary(PdfName.Names);
        PdfDictionary dests = names.getAsDictionary(PdfName.Dests);
        PdfArray name = dests.getAsArray(PdfName.Names);
        for (int i = 0; i < name.size(); i += 2) {
            PdfString original = name.getAsString(i);
            PdfString newName = new PdfString("new" + original.toString());
            name.set(i, newName);
            renamed.put(original.toString(), newName);
        }
        PdfDictionary page = pdfDoc.getPage(1).getPdfObject();
        PdfArray annotations = page.getAsArray(PdfName.Annots);
        PdfDictionary annotation;
        PdfDictionary action;
        PdfString n;
        for (int i = 0; i < annotations.size(); i++) {
            annotation = annotations.getAsDictionary(i);
            action = annotation.getAsDictionary(PdfName.A);
            if (action == null)
                continue;
            n = action.getAsString(PdfName.D);
            if (n != null && renamed.containsKey(n.toString())) {
                action.put(PdfName.D, renamed.get(n.toString()));
            }
        }
        pdfDoc.close();
    }
}
