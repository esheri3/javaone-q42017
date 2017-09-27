package com.whitehatsec.monolith;

import org.junit.*;

public class CryptoTest {
    @Test
    public void testEncryptAndDecrypt() throws Exception {
        String text = "something I want to encrypt";

        String cipherText = Crypto.encrypt(text);
        System.err.println("-- CIPHER TEXT: " + cipherText);

        String plainText = Crypto.decrypt(cipherText);
        System.err.println("-- PLAIN TEXT: " + plainText);

        Assert.assertEquals(text, plainText);
    }
}