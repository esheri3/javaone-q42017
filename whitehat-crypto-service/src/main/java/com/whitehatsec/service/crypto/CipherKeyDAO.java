package com.whitehatsec.service.crypto;

import io.dropwizard.hibernate.*;
import org.hibernate.*;

public class CipherKeyDAO extends AbstractDAO<CipherKey> {

    public CipherKeyDAO(SessionFactory factory) {
        super(factory);
    }

    public CipherKey save(CipherKey key) {
        return this.persist(key);
    }

    public CipherKey latest() {
        return (CipherKey) namedQuery("com.whitehatsec.service.crypto.CipherKey.findLatest")
            .setMaxResults(1)
            .uniqueResult();
    }

    /*public CipherKey find(String id) {
        return (CipherKey) currentSession()
            .createQuery("from CipherKey c where c.id = " + id)
            .uniqueResult();
    }*/

    public CipherKey find(int id) {
        return get(id);
    }
}
