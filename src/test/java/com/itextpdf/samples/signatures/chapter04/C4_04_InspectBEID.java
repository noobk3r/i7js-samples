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
//import javax.smartcardio.CardException;
//import javax.smartcardio.CardTerminal;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class C4_04_InspectBEID {
//
//    public static final String PHOTO = "results/chapter4/photo.jpg";
//
//    public static void main(String[] args) throws CardException, IOException {
//        CardReaders readers = new CardReaders();
//        for (CardTerminal terminal : readers.getReaders()) {
//            System.out.println(terminal.getName());
//        }
//        for (CardTerminal terminal : readers.getReadersWithCard()) {
//            System.out.println(terminal.getName());
//            SmartCard card = new SmartCard(terminal);
//            IdentityPojo id = BeIDFileFactory.getIdentity(card);
//            System.out.println(id.toString());
//            AddressPojo address = BeIDFileFactory.getAddress(card);
//            System.out.println(address);
//            PhotoPojo photo = BeIDFileFactory.getPhoto(card);
//            FileOutputStream fos = new FileOutputStream(PHOTO);
//            fos.write(photo.getPhoto());
//            fos.flush();
//            fos.close();
//        }
//    }
//}
