///*
//
//    This file is part of the iText (R) project.
//    Copyright (c) 1998-2016 iText Group NV
//
//*/
//
///**
//* Example written by Bruno Lowagie in answer to
//* http://stackoverflow.com/questions/33808187/get-row-count-of-a-multiline-acrofiled
//*/
//package com.itextpdf.samples.sandbox.acroforms;
//
//import com.itextpdf.forms.PdfAcroForm;
//import com.itextpdf.forms.fields.PdfFormField;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.geom.Rectangle;
//import com.itextpdf.kernel.pdf.PdfDictionary;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfName;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.kernel.pdf.PdfString;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.samples.GenericTest;
//import com.itextpdf.test.annotations.type.SampleTest;
//
//import java.io.File;
//
//import org.junit.experimental.categories.Category;
//
//@Category(SampleTest.class)
//public class MultiLineFieldCount extends GenericTest {
//    public static final String DEST
//            = "./target/test/resources/sandbox/acroforms/multi_line_field_count.pdf";
//    public static final String SRC
//            = "./src/test/resources/sandbox/acroforms/multiline.pdf";
//
//    public static void main(String[] args) throws Exception {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new MultiLineFieldCount().manipulatePdf(DEST);
//    }
//
//    @Override
//    protected void manipulatePdf(String dest) throws Exception {
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
//        StringBuilder sb = new StringBuilder();
//        for (String name : form.getFormFields().keySet()) {
//            int n = getInformation(form, name);
//            for (int i = 0; i < n; i++) {
//                sb.append(" *");
//            }
//            String filler = sb.toString();
//            form.getField(name).setValue(name + filler);
//        }
//        form.flattenFields();
//        pdfDoc.close();
//    }
//
//    public int getInformation(PdfAcroForm form, String name) {
//        PdfFormField item = form.getField(name);
//        // TODO No getMerged
//        PdfDictionary dict = item.getPdfObject();
//        // dict = item.getWidgets().get(0).getPdfObject();           // getMerged(0)
//        PdfString da = dict.getAsString(PdfName.DA);
//        Object[] da_values = PdfFormField.splitDAelements(da.toUnicodeString());
//        if (da_values == null) {
//            System.out.println("No default appearance");
//        }
//        PdfFont font = null;
//        String fontName = (String)da_values[PdfFormField.DA_FONT];
//        if (fontName != null) {
//            PdfDictionary dr = dict.getAsDictionary(PdfName.DR);
//            if (dr != null) {
//                PdfDictionary fontDict = dr.getAsDictionary(PdfName.Font);
//                // TODO is not dangerous, is it?
//                font = PdfFontFactory.createFont((PdfDictionary) fontDict.get(new PdfName(fontName)));
//            }
//        }
//        if (null == font) {
//            System.out.println("No BaseFont");
//        }
//        else {
//            // TODO No getPostscriptFontName
//            System.out.println("Basefont: " + font.getFontProgram().getFontNames().getFontName());
//            System.out.println("Size: " + da_values[PdfFormField.DA_SIZE]);
//            Integer size = (Integer)da_values[PdfFormField.DA_SIZE];
//            if (size == 0) {
//                return 1000;
//            }
//            Rectangle rect = form.getField(name).getWidgets().get(0).getRectangle().toRectangle();
//            float factor = 1; // TODO font.getPdfObject().getAsDictionary(PdfName.FontDescriptorBaseFont.BBOXURY, 1) - bf.getFontDescriptor(BaseFont.BBOXLLY, 1);
//            int rows = Math.round(rect.getHeight() / (size * factor) + 0.5f);
//            int columns = Math.round(rect.getWidth() / font.getWidth(" *", size) + 0.5f);
//            System.out.println("height: " + rect.getHeight() + "; width: " + rect.getWidth());
//            System.out.println("rows: " + rows + "; columns: " + columns);
//            return rows * columns;
//        }
//        return 1000;
//    }
//
//}
