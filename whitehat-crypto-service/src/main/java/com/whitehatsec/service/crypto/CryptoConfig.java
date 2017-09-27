package com.whitehatsec.service.crypto;

import com.fasterxml.jackson.annotation.*;
import io.dropwizard.*;
import io.dropwizard.db.*;

import javax.validation.*;
import javax.validation.constraints.*;

public class CryptoConfig extends io.dropwizard.Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDatabase() {
        return database;
    }

}