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
// TODO No com.itextpdf.smartcard analog

//package com.itextpdf.samples.signatures.chapter04;
//
//import com.itextpdf.kernel.geom.Rectangle;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.signatures.BouncyCastleDigest;
//import com.itextpdf.signatures.CrlClient;
//import com.itextpdf.signatures.CrlClientOnline;
//import com.itextpdf.signatures.ExternalDigest;
//import com.itextpdf.signatures.ExternalSignature;
//import com.itextpdf.signatures.OcspClient;
//import com.itextpdf.signatures.OcspClientBouncyCastle;
//import com.itextpdf.signatures.PdfSignatureAppearance;
//import com.itextpdf.signatures.PdfSigner;
//import com.itextpdf.signatures.TSAClient;
//
//import javax.smartcardio.CardException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.security.Security;
//import java.security.cert.Certificate;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//
// @Category(SampleTest.class)
//public class C4_06_SignWithBEID {
//
//    public static final String SRC = "src/main/resources/hello.pdf";
//    public static final String DEST = "results/chapter4/hello_beid.pdf";
//
//    public void sign(String src, String dest,
//                     SmartCardWithKey card, Certificate[] chain,
//                     PdfSigner.CryptoStandard subfilter,
//                     String reason, String location,
//                     Collection<CrlClient> crlList,
//                     OcspClient ocspClient,
//                     TSAClient tsaClient,
//                     int estimatedSize)
//            throws GeneralSecurityException, IOException {
//        // Creating the reader and the stamper
//        PdfReader reader = new PdfReader(src);
//        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), false);
//        // Creating the appearance
//        PdfSignatureAppearance appearance = signer.getSignatureAppearance()
//                .setReason(reason)
//                .setLocation(location)
//                .setReuseAppearance(false);
//        Rectangle rect = new Rectangle(36, 648, 200, 100);
//        appearance
//                .setPageRect(rect)
//                .setPageNumber(1);
//        signer.setFieldName("sig");
//        // Creating the signature
//        ExternalSignature eid = new EidSignature(card, "SHA256", "BC");
//        ExternalDigest digest = new BouncyCastleDigest();
//        signer.signDetached(digest, eid, chain, crlList, ocspClient, tsaClient, estimatedSize, subfilter);
//    }
//
//    public static void main(String[] args) throws CardException, GeneralSecurityException, IOException {
//        BouncyCastleProvider provider = new BouncyCastleProvider();
//        Security.addProvider(provider);
//
//        CardReaders readers = new CardReaders();
//        SmartCardWithKey card = new BeIDCard(readers.getReadersWithCard().get(0));
//        card.setSecure(true);
//        Certificate[] chain = BeIDCertificates.getSignCertificateChain(card);
//        Collection<CrlClient> crlList = new ArrayList<CrlClient>();
//        crlList.add(new CrlClientOnline(chain));
//        OcspClient ocspClient = new OcspClientBouncyCastle();
//        C4_06_SignWithBEID app = new C4_06_SignWithBEID();
//        app.sign(SRC, DEST, card, chain, PdfSigner.CryptoStandard.CMS,
//                "Test", "Ghent", crlList, ocspClient, null, 0);
//    }
//}
