package com.whitehatsec.service.crypto;

import java.security.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.hibernate.*;

@Path("/crypto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CryptoResource {

    private CipherKeyDAO cipherKeys;

    public CryptoResource(CipherKeyDAO cipherKeys) {
        this.cipherKeys = cipherKeys;
    }

    @POST
    @Path("/encrypt")
    @UnitOfWork
    public CipherText encrypt(PlainText plainText) throws GeneralSecurityException {
        CipherKey cipherKey = cipherKeys.latest();
        String transformation = CryptoUtils.transformation();
        String key = cipherKey.getValue();
        int keyId = cipherKey.getId();
        String iv = CryptoUtils.iv();

        String value = CryptoUtils.encrypt(transformation, key, iv, plainText.getValue());
        return new CipherText(keyId, transformation, iv, value);
    }

    @POST
    @Path("/decrypt")
    @UnitOfWork
    public PlainText decrypt(CipherText cipherText) throws GeneralSecurityException {
        CipherKey cipherKey = cipherKeys.find(cipherText.getKey());
        String transformation = cipherText.getTransformation();
        String key = cipherKey.getValue();
        String iv = cipherText.getIv();
        String text = cipherText.getValue();

        String value = CryptoUtils.decrypt(transformation, key, iv, text);
        boolean dated = !CryptoUtils.transformation().equals(transformation);
        return new PlainText(value, dated);
    }

}