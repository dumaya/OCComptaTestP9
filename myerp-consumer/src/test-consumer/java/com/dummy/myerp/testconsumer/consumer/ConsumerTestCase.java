package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;

/**
 * Classe mère des classes de test d'intégration de la couche Business
 */
public abstract class ConsumerTestCase {

    static {
        SpringRegistry.init();
    }

    /** {@link DaoProxy} */
    private static final DaoProxy DAO_PROXY = SpringRegistry.getDaoProxy();
    /** {@link ComptabiliteDaoImpl} */
    private static final ComptabiliteDaoImpl DAO_MANAGER = SpringRegistry.getComptabiliteDaoImpl();


    // ==================== Constructeurs ====================
    /**
     * Constructeur.
     */
    public ConsumerTestCase() {
    }


    // ==================== Getters/Setters ====================
    public static DaoProxy getDaoProxy() {
        return DAO_PROXY;
    }

    public static ComptabiliteDaoImpl getComptabiliteDaoImpl() {
        return DAO_MANAGER;
    }
}
