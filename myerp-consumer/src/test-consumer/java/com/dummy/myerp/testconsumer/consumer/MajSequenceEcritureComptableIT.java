package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class MajSequenceEcritureComptableIT extends ConsumerTestCase {

    /**
     * Constructeur.
     */

    public MajSequenceEcritureComptableIT() {
        super();
    }

    @BeforeClass
    public static void preparationCas() {
        // cas pour l'update
        SequenceEcritureComptable seq = new SequenceEcritureComptable("OD",1001,0);
        getComptabiliteDaoImpl().insertSequenceEcritureComptable(seq);
        // cas pour le delete
        SequenceEcritureComptable seq1 = new SequenceEcritureComptable("OD",1002,0);
        getComptabiliteDaoImpl().insertSequenceEcritureComptable(seq1);

    }

    /** Test  insert ecriture, insert ligne ecriture, update delete de la même ecriture  */
    @Test
    public void  insertSequenceEcritureTest() {
        //test insert
        SequenceEcritureComptable seq = new SequenceEcritureComptable("OD",1000,0);
        getComptabiliteDaoImpl().insertSequenceEcritureComptable(seq);
        try {
            assertThat(getComptabiliteDaoImpl().selectLastSequenceEcritureComptable("OD",1000)).isEqualTo(0);
        } catch (NotFoundException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test
    public void  updateSequenceEcritureTest() {
        SequenceEcritureComptable seqU = new SequenceEcritureComptable("OD",1001,0);
        Integer seq = null;
        try {
            seq = getComptabiliteDaoImpl().selectLastSequenceEcritureComptable(seqU.getCodeJournal(),seqU.getAnnee());
        } catch (NotFoundException e) {
            e.printStackTrace();
            Assert.fail("Preparation update KO");
        }
        seqU.setDerniereValeur(-2);
        try {
            getComptabiliteDaoImpl().updateSequenceEcritureComptable(seqU);
            assertThat(getComptabiliteDaoImpl().selectLastSequenceEcritureComptable("OD",1001)).isEqualTo(-2);
        } catch (NotFoundException e) {
            Assert.fail("Update KO");
            e.printStackTrace();
        }

    }
    @Test
    public void  deleteSequenceEcritureTest() {
        getComptabiliteDaoImpl().deleteSequenceEcritureComptable("OD",1002);

        try {
            getComptabiliteDaoImpl().selectLastSequenceEcritureComptable("OD",1002);
            Assert.fail();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public static void nettoyage() {
        // suppression du cas inséré + du cas de maj delete
        getComptabiliteDaoImpl().deleteSequenceEcritureComptable("OD",1000);
        getComptabiliteDaoImpl().deleteSequenceEcritureComptable("OD",1001);
        getComptabiliteDaoImpl().deleteSequenceEcritureComptable("OD",1002);

    }
}
