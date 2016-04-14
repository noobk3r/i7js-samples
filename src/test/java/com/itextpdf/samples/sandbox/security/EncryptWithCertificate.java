/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie.
 */
package com.itextpdf.samples.sandbox.security;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

/**
 * The file created using this example can not be opened, unless
 * you import the private key stored in test.p12 in your certificate store.
 * The password for the p12 file is kspass.
 */
// TODO We have to ignore these sample due to special security jars needed to be replaced on the server
// Note that there is no problem in the sample and feel free to use its snippets
@Ignore
@Category(SampleTest.class)
public class EncryptWithCertificate extends GenericTest {
    public static final String DEST
            = "./target/test/resources/sandbox/security/encrypt_with_certificate.pdf";
    public static final String SRC
            = "./src/test/resources/pdfs/hello_encrypted.pdf";
    public static final String PUBLIC
            = "./src/test/resources/encryption/test.cer";

    public static void main(String[] args) throws Exception {
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").
                    getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, java.lang.Boolean.FALSE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new EncryptWithCertificate().manipulatePdf(DEST);
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").
                    getDeclaredField("isRestricted");
            if (field.isAccessible()) {
                field.set(null, java.lang.Boolean.TRUE);
                field.setAccessible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Certificate getPublicCertificate(String path)
            throws IOException, CertificateException {
        FileInputStream is = new FileInputStream(path);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        return cert;
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        PdfWriter writer = new PdfWriter(new FileOutputStream(DEST));

        Certificate cert = getPublicCertificate(PUBLIC);

        writer.setEncryption(
                new Certificate[]{cert},
                new int[]{PdfWriter.ALLOW_PRINTING},
                PdfWriter.ENCRYPTION_AES_256);

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("My secret hello"));
        doc.close();
    }
}
