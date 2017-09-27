package com.whitehatsec.service.crypto;

import org.junit.*;

public class CryptoUtilsTest {

    @Test
    public void testIv() throws Exception {
        String iv = CryptoUtils.iv();
        Assert.assertNotNull(iv);
        Assert.assertTrue(iv.length() > 0);
    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        String transformation = "AES/GCM/PKCS5Padding";
        String key = "this is my super secret super long super random key";
        String iv = "EF9BA651683C70B90BD96FB9E65FB1D5733F39C2B892C0A582253375F9FABBFE10B9CFB7914E95D8E8F5A21FEC777201A1999759D9441B5A95E3F26CA5EB1111DF0AFC065AC4EB60F567B8ED7D34C5F5F42891508C998730896BF0D250489799CFCA678D5C2F6876B328C2173FA2299DA8A9267ED52800EFCA333FD724E25C53";
        String originalText = "this is my super secret message";

        String cipherText = CryptoUtils.encrypt(transformation, key, iv, originalText);
        System.err.println("-- CIPHER TEXT: " + cipherText);

        String plainText = CryptoUtils.decrypt(transformation, key, iv, cipherText);
        System.err.println("-- PLAIN TEXT: " + plainText);

        Assert.assertEquals(plainText, originalText);
    }

}