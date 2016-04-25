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

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.samples.SignatureTest;
import com.itextpdf.signatures.ICrlClient;
import com.itextpdf.signatures.CrlClientOnline;
import com.itextpdf.signatures.LtvVerification;
import com.itextpdf.signatures.IOcspClient;
import com.itextpdf.signatures.OcspClientBouncyCastle;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.SignatureUtil;
import com.itextpdf.signatures.ITSAClient;
import com.itextpdf.signatures.TSAClientBouncyCastle;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.List;
import java.util.Properties;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.fail;

@Ignore
@Category(SampleTest.class)
public class C5_04_LTV extends SignatureTest {
    // public static final String EXAMPLE1 = "results/chapter3/hello_token.pdf"; // TODO Uncomment after C3_11_SignWithToken revision
    // public static final String EXAMPLE2 = "results/chapter4/hello_smartcard_Signature.pdf"; // TODO Uncomment after C4_03_SignWithPKCS11SC revision
    // public static final String EXAMPLE3 = "results/chapter3/hello_cacert_ocsp_ts.pdf"; // TODO Uncomment after C3_09_SignWithTSA revision
    public static final String DEST = "./target/test/resources/signatures/chapter05/ltv_%s.pdf";

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        Properties properties = new Properties();
        properties.load(new FileInputStream("./src/test/resources/signatures/chapter03/key.properties"));
        String tsaUrl = properties.getProperty("TSAURL");
        String tsaUser = properties.getProperty("TSAUSERNAME");
        String tsaPass = properties.getProperty("TSAPASSWORD");
        C5_04_LTV app = new C5_04_LTV();
        ITSAClient tsa = new TSAClientBouncyCastle(tsaUrl, tsaUser, tsaPass, 6500, "SHA512");
        IOcspClient ocsp = new OcspClientBouncyCastle(null);
//        app.addLtv(EXAMPLE1, String.format(DEST, 1), ocsp, new CrlClientOnline(), tsa);
        System.out.println();
//        app.addLtv(EXAMPLE2, String.format(DEST, 2), ocsp, new CrlClientOnline(), tsa);
        System.out.println();
//        app.addLtv(EXAMPLE3, String.format(DEST, 3), ocsp, new CrlClientOnline(), tsa);
        System.out.println();
        app.addLtv(String.format(DEST, 1), String.format(DEST, 4), null, new CrlClientOnline(), tsa);
    }

    public void addLtv(String src, String dest, IOcspClient ocsp, ICrlClient crl, ITSAClient tsa) throws IOException, GeneralSecurityException {
        PdfReader reader = new PdfReader(src);
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), true);
        // TODO No verification field either in PdfDocument or in PdfSigner
        LtvVerification v = new LtvVerification(signer.getDocument()); //signer.getLtvVerification();
        SignatureUtil signUtil = new SignatureUtil(signer.getDocument());
        List<String> names = signUtil.getSignatureNames();
        String sigName = names.get(names.size() - 1);
        PdfPKCS7 pkcs7 = signUtil.verifySignature(sigName);
        if (pkcs7.isTsp())
            System.out.println("TIMESTAMP!");
        for (String name : names) {
            v.addVerification(name, ocsp, crl, LtvVerification.CertificateOption.WHOLE_CHAIN, LtvVerification.Level.OCSP_CRL, LtvVerification.CertificateInclusion.NO);
        }
        PdfSignatureAppearance sap = signer.getSignatureAppearance();
        // TODO No LtvTimestamp
        // LtvTimestamp.timestamp(sap, tsa, null);
    }

    @Test
    public void runTest() throws IOException, InterruptedException, GeneralSecurityException {
        new File("./target/test/resources/signatures/chapter05/").mkdirs();
        C5_04_LTV.main(null);

        String[] resultFiles = new String[]{ "hello_smartcard_Authentication.pdf", "hello_smartcard_Signature.pdf" };

        String destPath = String.format(outPath, "chapter04");
        String comparePath = String.format(cmpPath, "chapter04");

        String[] errors = new String[resultFiles.length];
        boolean error = false;

//        HashMap<Integer, List<Rectangle>> ignoredAreas = new HashMap<Integer, List<Rectangle>>() { {
//            put(1, Arrays.asList(new Rectangle(36, 648, 200, 100)));
//        }};

        for (int i = 0; i < resultFiles.length; i++) {
            String resultFile = resultFiles[i];
            String fileErrors = checkForErrors(destPath + resultFile, comparePath + "cmp_" + resultFile, destPath, /*ignoredAreas*/ null);
            if (fileErrors != null) {
                errors[i] = fileErrors;
                error = true;
            }
        }

        if (error) {
            fail(accumulateErrors(errors));
        }
    }
}
