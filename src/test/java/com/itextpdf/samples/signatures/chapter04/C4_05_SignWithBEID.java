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
//
//import javax.crypto.Cipher;
//import javax.smartcardio.CardException;
//import javax.smartcardio.CardTerminal;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.security.cert.X509Certificate;
//
//public class C4_05_SignWithBEID {
//
//    public static void main(String[] args) throws CardException, IOException, GeneralSecurityException {
//        CardReaders readers = new CardReaders();
//        for (CardTerminal terminal : readers.getReadersWithCard()) {
//            SmartCardWithKey card = new SmartCardWithKey(terminal, BeIDCertificates.AUTHENTICATION_KEY_ID, "RSA");
//            card.setPinProvider(new PinDialog(4));
//            byte[] signed = card.sign("ABCD".getBytes(), "SHA-256");
//            System.out.println(new String(signed));
//            X509Certificate cert = card.readCertificate(BeIDCertificates.AUTHN_CERT_FILE_ID);
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.DECRYPT_MODE, cert.getPublicKey());
//            System.out.println(new String(cipher.doFinal(signed)));
//        }
//    }
//}
