package com.itextpdf.samples.sandbox.fonts;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.utils.PdfMerger;

import java.io.*;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

// !!! IMPORTANT
// Because we want to use GenericTest and this particular sample produces too many outputs we will merge all outputs
// in one big file and check it through GenericTest
@Ignore
@Category(SampleTest.class)
public class MergeAndAddFont extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/merge_and_add_font.pdf";
    public static final String FONT = "./src/test/resources/sandbox/fonts/GravitasOne.ttf";

    public static final String[] FILE_A = {
            "./target/test/resources/sandbox/fonts/testA1.pdf", "./target/test/resources/sandbox/fonts/testA2.pdf",
            "./target/test/resources/sandbox/fonts/testA3.pdf"
    };
    public static final String[] FILE_B = {
            "./target/test/resources/sandbox/fonts/testB1.pdf", "./target/test/resources/sandbox/fonts/testB2.pdf",
            "./target/test/resources/sandbox/fonts/testB3.pdf"
    };
    public static final String[] FILE_C = {
            "./target/test/resources/sandbox/fonts/testC1.pdf", "./target/test/resources/sandbox/fonts/testC2.pdf",
            "./target/test/resources/sandbox/fonts/testC3.pdf"
    };
    public static final String[] CONTENT = {
            "abcdefgh", "ijklmnopq", "rstuvwxyz"
    };
    public static final String MERGED_A1 = "./target/test/resources/sandbox/fonts/testA_merged1.pdf";
    public static final String MERGED_A2 = "./target/test/resources/sandbox/fonts/testA_merged2.pdf";
    public static final String MERGED_B1 = "./target/test/resources/sandbox/fonts/testB_merged1.pdf";
    public static final String MERGED_B2 = "./target/test/resources/sandbox/fonts/testB_merged2.pdf";
    public static final String MERGED_C1 = "./target/test/resources/sandbox/fonts/testC_merged1.pdf";
    public static final String MERGED_C2 = "./target/test/resources/sandbox/fonts/testC_merged2.pdf";


    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MergeAndAddFont().manipulatePdf(DEST);
    }


    @Override
    protected void manipulatePdf(String dest) throws Exception {

        MergeAndAddFont app = new MergeAndAddFont();

        for (int i = 0; i < FILE_A.length; i++) {
            app.createPdf(FILE_A[i], CONTENT[i], true, true);
        }
        app.mergeFiles(FILE_A, MERGED_A1);
        app.mergeFiles(FILE_A, MERGED_A2);

        for (int i = 0; i < FILE_B.length; i++) {
            app.createPdf(FILE_B[i], CONTENT[i], true, false);
        }
        app.mergeFiles(FILE_B, MERGED_B1);
        app.mergeFiles(FILE_B, MERGED_B2);

        for (int i = 0; i < FILE_C.length; i++) {
            app.createPdf(FILE_C[i], CONTENT[i], false, false);
        }
        app.mergeFiles(FILE_C, MERGED_C1);
        app.embedFont(MERGED_C1, FONT, MERGED_C2);

    }

    public void createPdf(String filename, String text, boolean embedded, boolean subset) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(filename)));
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFont.createFont(FONT, "WinAnsi", embedded);
        font.setSubset(subset);
        doc.add(new Paragraph(text).setFont(font).setFontSize(12));
        doc.close();
    }

    // !!!IMPORTANT Mind that PdfMerger is a substitute both for PdfCopy ans PdfSmartCopy,
    // so the third argument of itext5 sample is redundant
    public void mergeFiles(String[] files, String result) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(result)));
        PdfMerger merger = new PdfMerger(pdfDoc);
        for (int i = 0; i < files.length; i++) {
            PdfDocument addedDoc = new PdfDocument(new PdfReader(new FileInputStream(files[i])));
            merger.addPages(addedDoc, 1, addedDoc.getNumOfPages());
            addedDoc.close();
        }
        merger.merge();
    }

    private void embedFont(String merged, String fontfile, String result) throws IOException {
        // the font file
        RandomAccessFile raf = new RandomAccessFile(fontfile, "r");
        byte fontbytes[] = new byte[(int) raf.length()];
        raf.readFully(fontbytes);
        raf.close();
        // create a new stream for the font file
        PdfStream stream = new PdfStream(fontbytes);
        stream.setCompressionLevel(PdfOutputStream.DEFAULT_COMPRESSION);
        stream.put(new PdfName("Length1"), new PdfNumber(fontbytes.length));
        // create a reader object
        PdfReader reader = new PdfReader(merged);
        PdfObject object;
        PdfDictionary font;
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(result)));
        PdfName fontname = new PdfName(PdfFont.createFont(fontfile, "WinAnsi", false)
                .getFontProgram()
                .getFontNames()
                .getFontName());
        int n = pdfDoc.getNumOfPdfObjects();
        for (int i = 0; i < n; i++) {
            object = pdfDoc.getPdfObject(i);
            if (object == null || !object.isDictionary())
                continue;
            font = (PdfDictionary) object;
            if (PdfName.FontDescriptor.equals(font.get(PdfName.Type))
                    && fontname.equals(font.get(PdfName.FontName))) {
                // TODO Implement addToBody(PdfStream)
                // PdfIndirectObject objref = pdfDoc.getWriter().addToBody(stream);
                // font.put(PdfName.FontFile2, objref.getIndirectReference());
            }
        }
        pdfDoc.close();
        reader.close();
    }
}
