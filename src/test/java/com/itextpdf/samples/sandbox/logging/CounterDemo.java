/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

///**
//* Example written by Bruno Lowagie in answer to:
//* http://stackoverflow.com/questions/26853894/continue-field-output-on-second-page-with-itextsharp
//*/
//package com.itextpdf.samples.sandbox.logging;
//
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfReader;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.core.testutils.annotations.type.SampleTest;
//import com.itextpdf.model.Document;
//import com.itextpdf.samples.GenericTest;
//import org.junit.Ignore;
//import org.junit.experimental.categories.Category;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Date;
//
//@Ignore
//@Category(SampleTest.class)
//public class CounterDemo extends GenericTest {
//    public static String SRC = "./src/test/resources/sandbox/acroforms/stationery.pdf";
//    public static String DEST = "./target/test/resources/sandbox/logging/counter_demo.pdf";
//
//    public static void main(String[] args) throws Exception {
//        new CounterDemo().manipulatePdf(DEST);
//    }
//
//    protected void manipulatePdf(String dest) throws Exception {
//        PdfReader reader = new PdfReader(new FileInputStream(SRC));
//        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));
//        Document doc = new Document(pdfDoc);
//
//
//        pdfDoc.close();
//    }
//
//
//    public class MyCounter{
//
//        public static final String LOG = "results/logging/counter.txt";
//        protected String name;
//        protected FileWriter writer;
//
//        private MyCounter(Class<?> klass) throws IOException {
//            this.name = klass.getName();
//            writer = new FileWriter(LOG, true);
//        }
//
//        public MyCounter getCounter(Class<?> klass) throws Exception {
//            try {
//                return new MyCounter(klass);
//            } catch (IOException e) {
//                // TODO There is no ExceptionConverter
//                throw new Exception(e);
//            }
//        }
//
//        public void read(long l) throws Exception {
//            if (writer == null)
//                throw new RuntimeException("No writer defined!");
//            try {
//                writer.write(String.format("[%s] %s: %s read\n", name, new Date().toString(), l));
//                writer.flush();
//            } catch (IOException e) {
//                // TODO There is no ExceptionConverter
//                throw new Exception(e);
//            }
//        }
//
//        public void written(long l) throws Exception {
//            if (writer == null)
//                throw new RuntimeException("No writer defined!");
//            try {
//                writer.write(String.format("[%s] %s: %s written\n", name, new Date().toString(), l));
//                writer.flush();
//            } catch (IOException e) {
//                // TODO There is no ExceptionConverter
//                throw new Exception(e);
//            }
//        }
//
//        public void close() throws IOException {
//            writer.close();
//        }
//    }
//}
