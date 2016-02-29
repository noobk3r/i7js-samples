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

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.signatures.CertificateInfo;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignaturePermissions;
import com.itextpdf.signatures.SignatureUtil;

import javax.smartcardio.CardException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.fail;

@Ignore
public class C5_02_SignatureInfo extends C5_01_SignatureIntegrity {
    public static final String EXAMPLE1 = "./src/test/resources/signatures/chapter05/step_4_signed_by_alice_bob_carol_and_dave.pdf";
    // public static final String EXAMPLE2 = "results/chapter3/hello_cacert_ocsp_ts.pdf"; // TODO Uncomment after C3_09_SignWithTSA revision
    // public static final String EXAMPLE3 = "results/chapter3/hello_token.pdf"; // TODO Uncomment after C3_11_SignWithToken revision
    public static final String EXAMPLE4 = "./src/test/resources/signatures/chapter05/hello_signed4.pdf";
    // public static final String EXAMPLE5 = "results/chapter4/hello_smartcard_Signature.pdf"; // TODO Uncomment after C4_03_SignWithPKCS11SC revision
    public static final String EXAMPLE6 = "./src/test/resources/signatures/chapter05/field_metadata.pdf";

    public static final  String expectedOutput = ""; //TODO

    public SignaturePermissions inspectSignature(SignatureUtil signUtil, PdfAcroForm form, String name, SignaturePermissions perms) throws GeneralSecurityException, IOException {
        //form.getField(name).getWidgets().get(0).getRectangle().toRectangle();
        // List<FieldPosition> fps = fields.getFieldPositions(name);
        if (form.getField(name).getWidgets() != null && form.getField(name).getWidgets().size() > 0) {
            // FieldPosition fp = fps.get(0);
            // Rectangle pos = fp.position;
            Rectangle pos = form.getField(name).getWidgets().get(0).getRectangle().toRectangle();
            if (pos.getWidth() == 0 || pos.getHeight() == 0) {
                System.out.println("Invisible signature");
            } else {
                // TODO One do not the page number until layout, so let's pass -1
                System.out.println(String.format("Field on page %s; llx: %s, lly: %s, urx: %s; ury: %s",
                        -1, pos.getLeft(), pos.getBottom(), pos.getRight(), pos.getTop()));
            }
        }

        PdfPKCS7 pkcs7 = super.verifySignature(signUtil, name);
        System.out.println("Digest algorithm: " + pkcs7.getHashAlgorithm());
        System.out.println("Encryption algorithm: " + pkcs7.getEncryptionAlgorithm());
        System.out.println("Filter subtype: " + pkcs7.getFilterSubtype());
        X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();
        System.out.println("Name of the signer: " + CertificateInfo.getSubjectFields(cert).getField("CN"));
        if (pkcs7.getSignName() != null)
            System.out.println("Alternative name of the signer: " + pkcs7.getSignName());
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        System.out.println("Signed on: " + date_format.format(pkcs7.getSignDate().getTime()));
        if (pkcs7.getTimeStampDate() != null) {
            System.out.println("TimeStamp: " + date_format.format(pkcs7.getTimeStampDate().getTime()));
            TimeStampToken ts = pkcs7.getTimeStampToken();
            System.out.println("TimeStamp service: " + ts.getTimeStampInfo().getTsa());
            System.out.println("Timestamp verified? " + pkcs7.verifyTimestampImprint());
        }
        System.out.println("Location: " + pkcs7.getLocation());
        System.out.println("Reason: " + pkcs7.getReason());
        PdfDictionary sigDict = signUtil.getSignatureDictionary(name);
        PdfString contact = sigDict.getAsString(PdfName.ContactInfo);
        if (contact != null)
            System.out.println("Contact info: " + contact);
        perms = new SignaturePermissions(sigDict, perms);
        System.out.println("Signature type: " + (perms.isCertification() ? "certification" : "approval"));
        System.out.println("Filling out fields allowed: " + perms.isFillInAllowed());
        System.out.println("Adding annotations allowed: " + perms.isAnnotationsAllowed());
        for (SignaturePermissions.FieldLock lock : perms.getFieldLocks()) {
            System.out.println("Lock: " + lock.toString());
        }
        return perms;
    }

    public void inspectSignatures(String path) throws IOException, GeneralSecurityException {
        System.out.println(path);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
        SignaturePermissions perms = null;
        SignatureUtil signUtil = new SignatureUtil(pdfDoc);
        List<String> names = signUtil.getSignatureNames();
        for (String name : names) {
            System.out.println("===== " + name + " =====");
            perms = inspectSignature(signUtil, form, name, perms);
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        C5_02_SignatureInfo app = new C5_02_SignatureInfo();
        app.inspectSignatures(EXAMPLE1);
        // app.inspectSignatures(EXAMPLE2);
        // app.inspectSignatures(EXAMPLE3);
        app.inspectSignatures(EXAMPLE4);
        // app.inspectSignatures(EXAMPLE5);
        app.inspectSignatures(EXAMPLE6);
    }

    @Test
    public void runTest() throws GeneralSecurityException, IOException, InterruptedException, CardException {
        setupSystemOutput();
        C5_02_SignatureInfo.main(null);
        String sysOut = getSystemOutput();

        if (!sysOut.equals(expectedOutput)) {
            fail("Unexpected output.");
        }
    }
}
