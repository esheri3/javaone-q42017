package com.whitehatsec.service.crypto;

import io.dropwizard.*;
import io.dropwizard.db.*;
import io.dropwizard.hibernate.*;
import io.dropwizard.setup.*;

public class CryptoApp extends Application<CryptoConfig> {
    private final HibernateBundle<CryptoConfig> hibernate = new HibernateBundle<CryptoConfig>(CipherKey.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(CryptoConfig configuration) {
            return configuration.getDatabase();
        }
    };

    @Override
    public String getName() {
        return "whitehat-crypto-service";
    }

    @Override
    public void initialize(Bootstrap<CryptoConfig> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(CryptoConfig configuration, Environment environment) {
        // register hibernate dao
        CipherKeyDAO dao = new CipherKeyDAO(hibernate.getSessionFactory());
        CryptoResource resource = new CryptoResource(dao);
        environment.jersey().register(resource);
    }

    public static void main(String[] args) throws Exception {
        new CryptoApp().run(args);
    }
}