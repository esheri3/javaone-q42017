package com.whitehatsec.monolith;

import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class Crypto {
    // secret key for encryption / decryption
    private final static String KEY = "javaone2";

    // encrypt plaintext and return ciphertext
    public static String encrypt(String plainText) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(new DESKeySpec(KEY.getBytes()));

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    // decrypt ciphertext and return plaintext
    public static String decrypt(String cipherText) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(new DESKeySpec(KEY.getBytes()));

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] buffer = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(buffer));
    }
}