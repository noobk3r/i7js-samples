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
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.signatures.CertificateInfo;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignaturePermissions;
import com.itextpdf.signatures.SignatureUtil;
import com.itextpdf.test.annotations.type.SampleTest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;
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
import java.text.SimpleDateFormat;
import java.util.List;

@Ignore
@Category(SampleTest.class)
public class C5_02_SignatureInfo extends C5_01_SignatureIntegrity {
    // TODO Make resources from cmp files after revision
    public static final String EXAMPLE1 = "./src/test/resources/pdfs/step_4_signed_by_alice_bob_carol_and_dave.pdf";
    // public static final String EXAMPLE2 = "results/chapter3/hello_cacert_ocsp_ts.pdf"; // TODO Uncomment after C3_09_SignWithTSA revision
    // public static final String EXAMPLE3 = "results/chapter3/hello_token.pdf"; // TODO Uncomment after C3_11_SignWithToken revision
    public static final String EXAMPLE4 = "./src/test/resources/pdfs/hello_signed4.pdf";
    // public static final String EXAMPLE5 = "results/chapter4/hello_smartcard_Signature.pdf"; // TODO Uncomment after C4_03_SignWithPKCS11SC revision
    public static final String EXAMPLE6 = "./src/test/resources/pdfs/field_metadata.pdf";

    public static final  String expectedOutput = "===== sig1 =====\r\n" +
            "\r\n" +
            "Field on page 1; llx: 36.0, lly: 728.02, urx: 559.0; ury: 779.02\r\n" +
            "Signature covers whole document: false\r\n" +
            "Document revision: 1 of 4\r\n" +
            "Integrity check OK? true\r\n" +
            "Digest algorithm: SHA256\r\n" +
            "Encryption algorithm: RSA\r\n" +
            "Filter subtype: /adbe.pkcs7.detached\r\n" +
            "Name of the signer: Alice Specimen\r\n" +
            "Signed on: 2016-02-23 11:55:01.00\r\n" +
            "Location: \r\n" +
            "Reason: \r\n" +
            "Contact info: \r\n" +
            "Signature type: certification\r\n" +
            "Filling out fields allowed: true\r\n" +
            "Adding annotations allowed: false\r\n" +
            "===== sig2 =====\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "Field on page 1; llx: 36.0, lly: 629.04, urx: 559.0; ury: 680.04\r\n" +
            "Signature covers whole document: false\r\n" +
            "Document revision: 2 of 4\r\n" +
            "Integrity check OK? true\r\n" +
            "Digest algorithm: SHA256\r\n" +
            "Encryption algorithm: RSA\r\n" +
            "Filter subtype: /adbe.pkcs7.detached\r\n" +
            "Name of the signer: Bob Specimen\r\n" +
            "Signed on: 2016-02-23 11:55:02.00\r\n" +
            "Location: \r\n" +
            "Reason: \r\n" +
            "Contact info: \r\n" +
            "Signature type: approval\r\n" +
            "Filling out fields allowed: true\r\n" +
            "Adding annotations allowed: false\r\n" +
            "Lock: /Include[sig1 approved_bob sig2 ]\r\n" +
            "===== sig3 =====\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "Field on page 1; llx: 36.0, lly: 530.05, urx: 559.0; ury: 581.05\r\n" +
            "Signature covers whole document: false\r\n" +
            "Document revision: 3 of 4\r\n" +
            "Integrity check OK? true\r\n" +
            "Digest algorithm: SHA256\r\n" +
            "Encryption algorithm: RSA\r\n" +
            "Filter subtype: /adbe.pkcs7.detached\r\n" +
            "Name of the signer: Carol Specimen\r\n" +
            "Signed on: 2016-02-23 11:55:02.00\r\n" +
            "Location: \r\n" +
            "Reason: \r\n" +
            "Contact info: \r\n" +
            "Signature type: approval\r\n" +
            "Filling out fields allowed: true\r\n" +
            "Adding annotations allowed: false\r\n" +
            "Lock: /Include[sig1 approved_bob sig2 ]\r\n" +
            "Lock: /Exclude[approved_dave sig4 ]\r\n" +
            "===== sig4 =====\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "Field on page 1; llx: 36.0, lly: 431.07, urx: 559.0; ury: 482.07\r\n" +
            "Signature covers whole document: true\r\n" +
            "Document revision: 4 of 4\r\n" +
            "Integrity check OK? true\r\n" +
            "Digest algorithm: SHA256\r\n" +
            "Encryption algorithm: RSA\r\n" +
            "Filter subtype: /adbe.pkcs7.detached\r\n" +
            "Name of the signer: Dave Specimen\r\n" +
            "Signed on: 2016-02-23 11:55:02.00\r\n" +
            "Location: \r\n" +
            "Reason: \r\n" +
            "Contact info: \r\n" +
            "Signature type: approval\r\n" +
            "Filling out fields allowed: false\r\n" +
            "Adding annotations allowed: false\r\n" +
            "Lock: /Include[sig1 approved_bob sig2 ]\r\n" +
            "Lock: /Exclude[approved_dave sig4 ]\r\n" +
            "\r\n" +
            "===== sig =====\r\n" +
            "\r\n" +
            "Field on page 1; llx: 36.0, lly: 648.0, urx: 236.0; ury: 748.0\r\n" +
            "Signature covers whole document: true\r\n" +
            "Document revision: 1 of 1\r\n" +
            "Integrity check OK? true\r\n" +
            "Digest algorithm: RIPEMD160\r\n" +
            "Encryption algorithm: RSA\r\n" +
            "Filter subtype: /ETSI.CAdES.detached\r\n" +
            "Name of the signer: Bruno Specimen\r\n" +
            "Signed on: 2016-02-23 12:24:30.00\r\n" +
            "Location: Ghent\r\n" +
            "Reason: Test 4\r\n" +
            "Contact info: \r\n" +
            "Signature type: approval\r\n" +
            "Filling out fields allowed: true\r\n" +
            "Adding annotations allowed: true\r\n" +
            "\r\n" +
            "===== Signature1 =====\r\n" +
            "\r\n" +
            "Field on page 1; llx: 46.0674, lly: 472.172, urx: 332.563; ury: 726.831\r\n" +
            "Signature covers whole document: true\r\n" +
            "Document revision: 1 of 1\r\n" +
            "Integrity check OK? true\r\n" +
            "Digest algorithm: SHA256\r\n" +
            "Encryption algorithm: RSA\r\n" +
            "Filter subtype: /adbe.pkcs7.detached\r\n" +
            "Name of the signer: Bruno Specimen\r\n" +
            "Alternative name of the signer: Bruno L. Specimen\r\n" +
            "Signed on: 2016-02-23 12:22:40.00\r\n" +
            "Location: Ghent\r\n" +
            "Reason: Test metadata\r\n" +
            "Contact info: 555 123 456\r\n" +
            "Signature type: approval\r\n" +
            "Filling out fields allowed: true\r\n" +
            "Adding annotations allowed: true\r\n" +
            "\r\n"; 

    public SignaturePermissions inspectSignature(PdfDocument pdfDoc, SignatureUtil signUtil, PdfAcroForm form, String name, SignaturePermissions perms) throws GeneralSecurityException, IOException {
        if (form.getField(name).getWidgets() != null && form.getField(name).getWidgets().size() > 0) {
            int pageNum = 0;
            Rectangle pos = form.getField(name).getWidgets().get(0).getRectangle().toRectangle();
            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
                PdfPage page = pdfDoc.getPage(i);
                for (PdfAnnotation annot : page.getAnnotations()) {
                    System.out.println();
                    if (PdfName.Sig.equals(annot.getPdfObject().get(PdfName.FT)) && name.equals(annot.getPdfObject().get(PdfName.T).toString())) {
                        pageNum = pdfDoc.getPageNumber(page);
                        break;
                    }
                }
            }
            if (pos.getWidth() == 0 || pos.getHeight() == 0) {
                System.out.println("Invisible signature");
            } else {
                System.out.println(String.format("Field on page %s; llx: %s, lly: %s, urx: %s; ury: %s", pageNum, pos.getLeft(), pos.getBottom(), pos.getRight(), pos.getTop()));
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
        // System.out.println(path);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
        SignaturePermissions perms = null;
        SignatureUtil signUtil = new SignatureUtil(pdfDoc);
        List<String> names = signUtil.getSignatureNames();
        for (String name : names) {
            System.out.println("===== " + name + " =====");
            perms = inspectSignature(pdfDoc, signUtil, form, name, perms);
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
        new File("./target/test/resources/signatures/chapter05/").mkdirs();
        setupSystemOutput();
        C5_02_SignatureInfo.main(null);
        String sysOut = getSystemOutput();
        Assert.assertEquals("Unexpected output", expectedOutput, sysOut);
    }
}
