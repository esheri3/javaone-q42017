package com.whitehatsec.service.crypto;

import java.security.*;
import java.security.spec.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CryptoUtils {

    private final static String DEFAULT_TRANSFORMATION = "AES/GCM/PKCS5Padding";

    private final static int DEFAULT_ITERATIONS = 65535;

    private final static int GCM_TAG_LENGTH = 128; // in bytes

    public static String transformation() {
        return DEFAULT_TRANSFORMATION;
    }

    public static String iv() {
         byte[] buffer = new byte[16];
         SecureRandom secureRandom = null;

         try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
         } catch (NoSuchAlgorithmException e) {
             throw new RuntimeException(e);
         }

         secureRandom.nextBytes(buffer);
         return Base64.getEncoder().encodeToString(buffer);
    }

    public static String encrypt(String transformation, String key, String iv, String plainText) throws GeneralSecurityException {
        String[] parts = transformation.split("/");

        if (parts.length != 3) {
            throw new IllegalArgumentException("malformed transformation: " + transformation);
        }

        String algo = parts[0];
        String mode = parts[1];

        // construct cipher instance
        Cipher cipher = Cipher.getInstance(transformation);

        // create key spec given provided key
        SecretKey secretKey = null;
        switch (algo) {
            case "AES":
                secretKey = computeAESKey(key.toCharArray(), iv.getBytes(), DEFAULT_ITERATIONS);
                break;
            default:
                throw new IllegalArgumentException("unsupported algorithm: " + algo);
        }

        // create param spec based on mode
        AlgorithmParameterSpec paramSpec = null;
        switch (mode) {
            case "GCM":
                paramSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv.getBytes());
                break;
            default:
                throw new IllegalArgumentException("unsupported algorithm: " + algo);
        }

        // initializer cipher for encryption using key & param
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

        // include plaintext and encrypt
        byte[] buffer = plainText.getBytes();
        byte[] cipherText = cipher.doFinal(buffer, 0, buffer.length);

        // return base64 encoded cipher text
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String transformation, String key, String iv, String cipherText) throws GeneralSecurityException {
        String[] parts = transformation.split("/");

        if (parts.length != 3) {
            throw new GeneralSecurityException("malformed transformation: " + transformation);
        }

        String algo = parts[0];
        String mode = parts[1];

        // construct cipher instance
        Cipher cipher = Cipher.getInstance(transformation);

        // create key spec given provided key
        SecretKey secretKey = null;
        switch (algo) {
            case "AES":
                secretKey = computeAESKey(key.toCharArray(), iv.getBytes(), DEFAULT_ITERATIONS);
                break;
            default:
                throw new IllegalArgumentException("unsupported algorithm: " + algo);
        }

        // create param spec based on algorithm name
        AlgorithmParameterSpec paramSpec = null;
        switch (mode) {
            case "GCM":
                paramSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv.getBytes());
                break;
            default:
                throw new GeneralSecurityException("unsupported algorithm: " + algo);
        }

        // initializer cipher for decryption using key & param
        cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

        // include ciphertext and decrypt
        byte[] buffer = Base64.getDecoder().decode(cipherText);
        byte[] plainText = cipher.doFinal(buffer, 0, buffer.length);

        // return plaintext string
        return new String(plainText);
    }

    private static SecretKey computeAESKey(char[] password, byte[] salt, int iterations) throws GeneralSecurityException {
        // FIXME: should use AES-256 but requires policy change
        int length = 128;

        // construct factory using PBKDF2
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // compute key spec
        KeySpec spec = new PBEKeySpec(password, salt, iterations, length);

        // create AES compatible secret key
        SecretKey tmp = keyFactory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

}