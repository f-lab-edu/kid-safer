package com.flab.kidsafer.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    public static final String ENCRYPTION_TYPE = "SHA-256";

    public static String getSHA256(String str) {
        String SHA = null;
        try {
            MessageDigest sh = MessageDigest.getInstance(ENCRYPTION_TYPE);
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte perbyteData : byteData) {
                sb.append(Integer.toString((perbyteData & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return SHA;
    }
}
