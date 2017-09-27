package com.whitehatsec.service.crypto;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;

@Entity
@NamedQueries(
    @NamedQuery(
        name = "com.whitehatsec.service.crypto.CipherKey.findLatest",
        query = "from CipherKey c order by id desc"
    )
)
public class CipherKey {

    @Id
    private int id;

    @Column
    private String value;

    public CipherKey() {
        // empty ctor
    }

    public CipherKey(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

}