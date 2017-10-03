package com.whitehatsec.service.crypto;

import com.fasterxml.jackson.annotation.*;

public class CipherText {

    private int key;

    private String transformation;

    private String iv;

    private String value;

    public CipherText() {
        // empty ctor
    }

    public CipherText(int key, String transformation, String iv, String value) {
        this.key = key;
        this.transformation = transformation;
        this.iv = iv;
        this.value = value;
    }

    @JsonProperty("key")
    public int getKey() {
        return key;
    }

    @JsonProperty("transformation")
    public String getTransformation() {
        return transformation;
    }

    @JsonProperty("iv")
    public String getIv() {
        return iv;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

}