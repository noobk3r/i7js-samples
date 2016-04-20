/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part3.chapter12;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.samples.SignatureTest;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.ExternalDigest;
import com.itextpdf.signatures.ExternalSignature;
import com.itextpdf.signatures.OcspClient;
import com.itextpdf.signatures.OcspClientBouncyCastle;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;
import com.itextpdf.signatures.TSAClient;
import com.itextpdf.signatures.TSAClientBouncyCastle;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.fail;

@Ignore("Put property file with valid data")
@Category(SampleTest.class)
public class Listing_12_21_TimestampOCSP extends SignatureTest {
    public static String SRC = "./src/test/resources/book/part3/chapter12/cmp_Listing_12_15_Signatures_hello.pdf";
    public static String SIGNED0 = "./target/test/resources/book/part3/chapter12/Listing_12_21_TimestampOCSP_without.pdf";
    public static String SIGNED1 = "./target/test/resources/book/part3/chapter12/Listing_12_21_TimestampOCSP_ts.pdf";
    public static String SIGNED2 = "./target/test/resources/book/part3/chapter12/Listing_12_21_TimestampOCSP_ocsp.pdf";
    public static String SIGNED3 = "./target/test/resources/book/part3/chapter12/Listing_12_21_TimestampOCSP_ts_oscp.pdf";

    public static String[] RESULT = {
            SIGNED0,
//            SIGNED1,
            SIGNED2
//            SIGNED3
    };
    public static String[] CMP_RESULT = {
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_21_TimestampOCSP_without.pdf",
//            SIGNED1,
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_21_TimestampOCSP_ocsp.pdf",
//            SIGNED3
    };

    /**
     * A properties file that is PRIVATE.
     * You should make your own properties file and adapt this line.
     */
    public static String PATH
            = "./src/test/resources/encryption/key.properties";
    // public static String PATH = "c:/users/ars18wrw/key.properties";
    public static Properties properties = new Properties();

    public void signPdf(String src, String dest, boolean withTS, boolean withOCSP)
            throws IOException, GeneralSecurityException {
        // Keystore and certificate chain
        String keystore = properties.getProperty("PRIVATE");
        String password = properties.getProperty("PASSWORD");
        KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
        ks.load(new FileInputStream(keystore), password.toCharArray());
        String alias = (String)ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey)ks.getKey(alias, password.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);
        // Creating the reader and the signer
        PdfReader reader = new PdfReader(SRC);
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), false);
        // appearance
        PdfSignatureAppearance sap = signer.getSignatureAppearance();
        sap.setReason("I'm approving this.");
        sap.setLocation("Foobar");
        sap.setPageNumber(1);
        sap.setPageRect(new Rectangle(72, 632, 200, 100));
        signer.setFieldName("Signature");
        // preserve some space for the contents
        // digital signature
        ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
        // If we add a time stamp:
        TSAClient tsc = null;
        if (withTS) {
            String tsa_url    = properties.getProperty("TSA");
            String tsa_login  = properties.getProperty("TSA_LOGIN");
            String tsa_passw  = properties.getProperty("TSA_PASSWORD");
            tsc = new TSAClientBouncyCastle(tsa_url, tsa_login, tsa_passw);
        }
        // If we use OCSP:
        OcspClient ocsp = null;
        if (withOCSP) {
            ocsp = new OcspClientBouncyCastle();
        }
        ExternalDigest digest = new BouncyCastleDigest();
        signer.signDetached(digest, es, chain, null, ocsp, tsc, 0, PdfSigner.CryptoStandard.CMS);
    }


    public static void main(String[] args) throws Exception {
        new Listing_12_21_TimestampOCSP().manipulatePdf(SRC);
    }

    protected void manipulatePdf(String src) throws IOException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        properties.load(new FileInputStream(PATH));
        signPdf(src, SIGNED0, false, false);
//        signPdf(src, SIGNED1, true, false);
        signPdf(src, SIGNED2, false, true);
//        signPdf(src, SIGNED3, true, true);
    }

    @Test
    public void runTest() throws Exception {
        Listing_12_21_TimestampOCSP.main(null);

        String[] errors = new String[RESULT.length];
        boolean error = false;

        HashMap<Integer, List<Rectangle>> ignoredAreas = new HashMap<Integer, List<Rectangle>>() { {
            put(1, Arrays.asList(new Rectangle(72, 632, 200, 100)));
        }};

        for (int i = 0; i < RESULT.length; i++) {
            String fileErrors = checkForErrors(RESULT[i], CMP_RESULT[i], "./target/test/resources/book/part3/chapter12/", ignoredAreas);
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
