package com.whitehatsec.service.crypto;

import com.fasterxml.jackson.annotation.*;

public class PlainText {

    private String value;

    private boolean dated;

    public PlainText() {
        // empty ctor
    }

    public PlainText(String value, boolean dated) {
        this.value = value;
        this.dated = dated;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("dated")
    public boolean isDated() {
        return dated;
    }

}