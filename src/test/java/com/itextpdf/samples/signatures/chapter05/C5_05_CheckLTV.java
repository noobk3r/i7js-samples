/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/*
 * This class is part of the white paper entitled
 * "Digital Signatures for PDF documents"
 * written by Bruno Lowagie
 *
 * For more info, go to: http://itextpdf.com/learn
 */
package com.itextpdf.samples.signatures.chapter05;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.samples.SignatureTest;
import com.itextpdf.signatures.CertificateInfo;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignatureUtil;
import com.itextpdf.test.annotations.type.SampleTest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.smartcardio.CardException;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.List;

@Ignore
@Category(SampleTest.class)
public class C5_05_CheckLTV extends SignatureTest {
    // TODO Uncomment after C5_04_LTV revision
//    public static final String EXAMPLE1 = "results/chapter5/ltv_1.pdf";
//    public static final String EXAMPLE2 = "results/chapter5/ltv_2.pdf";
//    public static final String EXAMPLE3 = "results/chapter5/ltv_3.pdf";
//    public static final String EXAMPLE4 = "results/chapter5/ltv_4.pdf";

    public static final  String expectedOutput = "";

    public PdfPKCS7 verifySignature(SignatureUtil signUtil, String name) throws GeneralSecurityException, IOException {
        System.out.println("Signature covers whole document: " + signUtil.signatureCoversWholeDocument(name));
        System.out.println("Document revision: " + signUtil.getRevision(name) + " of " + signUtil.getTotalRevisions());
        PdfPKCS7 pkcs7 = signUtil.verifySignature(name);
        System.out.println("Integrity check OK? " + pkcs7.verify());
        System.out.println("Digest algorithm: " + pkcs7.getHashAlgorithm());
        System.out.println("Encryption algorithm: " + pkcs7.getEncryptionAlgorithm());
        System.out.println("Filter subtype: " + pkcs7.getFilterSubtype());
        X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();
        System.out.println("Name of the signer: " + CertificateInfo.getSubjectFields(cert).getField("CN"));
        return pkcs7;
    }

    public void verifySignatures(String path) throws IOException, GeneralSecurityException {
        System.out.println(path);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));
        SignatureUtil signUtil = new SignatureUtil(pdfDoc);
        List<String> names = signUtil.getSignatureNames();
        for (String name : names) {
            System.out.println("===== " + name + " =====");
            verifySignature(signUtil, name);
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        C5_05_CheckLTV app = new C5_05_CheckLTV();
//        app.verifySignatures(EXAMPLE1);
//        app.verifySignatures(EXAMPLE2);
//        app.verifySignatures(EXAMPLE3);
//        app.verifySignatures(EXAMPLE4);
    }

    @Test
    public void runTest() throws GeneralSecurityException, IOException, InterruptedException, CardException {
        new File("./target/test/resources/signatures/chapter05/").mkdirs();
        setupSystemOutput();
        C5_05_CheckLTV.main(null);
        String sysOut = getSystemOutput();

        Assert.assertEquals("Unexpected output.", expectedOutput, sysOut);
    }
}
