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
import com.itextpdf.samples.SignatureTest;
import com.itextpdf.signatures.CertificateVerifier;
import com.itextpdf.signatures.LtvVerification;
import com.itextpdf.signatures.LtvVerifier;
import com.itextpdf.signatures.VerificationOK;
import com.itextpdf.test.annotations.type.SampleTest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.smartcardio.CardException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Ignore
@Category(SampleTest.class)
public class C5_06_ValidateLTV extends SignatureTest {
    public static final String ADOBE = "./src/test/resources/signatures/chapter05/adobeRootCA.cer";
    // TODO Uncomment after C5_04_LTV revision
//    public static final String EXAMPLE1 = "results/chapter5/ltv_1.pdf";
//    public static final String EXAMPLE2 = "results/chapter5/ltv_2.pdf";
//    public static final String EXAMPLE3 = "results/chapter5/ltv_3.pdf";
//    public static final String EXAMPLE4 = "results/chapter5/ltv_4.pdf";

    public static final  String expectedOutput = "";

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        C5_06_ValidateLTV app = new C5_06_ValidateLTV();
        // System.out.println(EXAMPLE1);
        // app.validate(new PdfDocument(new PdfReader(EXAMPLE1)));
        System.out.println();
        // System.out.println(EXAMPLE2);
        // app.validate(new PdfDocument(new PdfReader(EXAMPLE2)));
        System.out.println();
        // System.out.println(EXAMPLE3);
        // app.validate(new PdfDocument(new PdfReader(EXAMPLE3)));
        System.out.println();
        // System.out.println(EXAMPLE4);
        // app.validate(new PdfDocument(new PdfReader(EXAMPLE4)));
    }

    public void validate(PdfDocument pdfDoc) throws IOException, GeneralSecurityException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        ks.setCertificateEntry("adobe",
                cf.generateCertificate(new FileInputStream(ADOBE)));

        CertificateVerifier custom = new CertificateVerifier(null) {
            public List<VerificationOK> verify(X509Certificate signCert,
                                               X509Certificate issuerCert, Date signDate)
                    throws GeneralSecurityException, IOException {
                System.out.println(signCert.getSubjectDN().getName() + ": ALL VERIFICATIONS DONE");
                return new ArrayList<VerificationOK>();
            }
        };

        LtvVerifier data = new LtvVerifier(pdfDoc);
        data.setRootStore(ks);
        data.setCertificateOption(LtvVerification.CertificateOption.WHOLE_CHAIN);
        data.setVerifier(custom);
        data.setOnlineCheckingAllowed(false);
        data.setVerifyRootCertificate(false);
        List<VerificationOK> list = new ArrayList<VerificationOK>();
        try {
            data.verify(list);
        } catch (GeneralSecurityException e) {
            System.err.println(e.getMessage());
        }
        System.out.println();
        if (list.size() == 0) {
            System.out.println("The document can't be verified");
        }
        for (VerificationOK v : list)
            System.out.println(v.toString());
    }

    @Test
    public void runTest() throws GeneralSecurityException, IOException, InterruptedException, CardException {
        new File("./target/test/resources/signatures/chapter05/").mkdirs();
        setupSystemOutput();
        C5_06_ValidateLTV.main(null);
        String sysOut = getSystemOutput();

        Assert.assertEquals("Unexpected output.", expectedOutput, sysOut);
    }
}
