package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
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
public class MajEcritureComptableIT extends ConsumerTestCase {

    /**
     * Constructeur.
     */

    public MajEcritureComptableIT() {
        super();
    }

    @BeforeClass
    public static void preparationCas() {
        // cas pour l'update
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("OD", "Achat"));
        vEcritureComptable.setDate(new Date(TimeUnit.SECONDS.toMillis(13420840L)));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(121)));
        vEcritureComptable.setReference("OD-1970/00001");
        getComptabiliteDaoImpl().insertEcritureComptable(vEcritureComptable);
        // cas pour le delete
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setJournal(new JournalComptable("OD", "Achat"));
        vEcritureComptable1.setDate(new Date(TimeUnit.SECONDS.toMillis(13420840L)));
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(121)));
        vEcritureComptable1.setReference("OD-1970/00002");
        getComptabiliteDaoImpl().insertEcritureComptable(vEcritureComptable1);

    }

    /** Test  insert ecriture, insert ligne ecriture, update delete de la même ecriture  */
    @Test
    public void  insertEcritureTest() {
        //test insert
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("OD", "Achat"));
        vEcritureComptable.setDate(new Date(TimeUnit.SECONDS.toMillis(364054988L)));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(121)));
        vEcritureComptable.setReference("OD-1981/00001");
        getComptabiliteDaoImpl().insertEcritureComptable(vEcritureComptable);

        assertThat(vEcritureComptable.getId()).isNotNull();
    }

    @Test
    public void  updateEcritureTest() {
        EcritureComptable vEcritureComptable = null;
        try {
            vEcritureComptable = getComptabiliteDaoImpl().getEcritureComptableByRef("OD-1970/00001");
        } catch (NotFoundException e) {
            Assert.fail("Preparation update KO");
            e.printStackTrace();
        }
        vEcritureComptable.setLibelle("libelle maj");
        vEcritureComptable.getListLigneEcriture().get(0).setCredit(new BigDecimal(55));

        getComptabiliteDaoImpl().updateEcritureComptable(vEcritureComptable);

        assertThat(vEcritureComptable.getLibelle()).isEqualTo("libelle maj");
        assertThat(vEcritureComptable.getListLigneEcriture().get(0).getCredit()).isEqualByComparingTo(new BigDecimal(55));

    }
    @Test
    public void  deleteEcritureTest() {
        EcritureComptable vEcritureComptable = null;
        try {
            vEcritureComptable = getComptabiliteDaoImpl().getEcritureComptableByRef("OD-1970/00002");
        } catch (NotFoundException e) {
            Assert.fail("Preparation delete KO");
            e.printStackTrace();
        }
        getComptabiliteDaoImpl().deleteEcritureComptable(vEcritureComptable.getId());

        EcritureComptable ecritureComptable;
        try {
            ecritureComptable = getComptabiliteDaoImpl().getEcritureComptable(vEcritureComptable.getId());
            Assert.fail();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public static void nettoyage() {
        // suppression du cas inséré + du cas de maj delete
        EcritureComptable vEcritureComptable1 = null;
        try {
            vEcritureComptable1 = getComptabiliteDaoImpl().getEcritureComptableByRef("OD-1970/00001");
        } catch (NotFoundException e) {
            Assert.fail("Nettoyage update était KO");
            e.printStackTrace();
        }
        getComptabiliteDaoImpl().deleteEcritureComptable(vEcritureComptable1.getId());
        EcritureComptable vEcritureComptable2 = null;
        try {
            vEcritureComptable2 = getComptabiliteDaoImpl().getEcritureComptableByRef("OD-1970/00002");
            getComptabiliteDaoImpl().deleteEcritureComptable(vEcritureComptable2.getId());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException ex) {

        }


        EcritureComptable vEcritureComptable3 = null;
        try {
            vEcritureComptable3 = getComptabiliteDaoImpl().getEcritureComptableByRef("OD-1981/00001");
        } catch (NotFoundException e) {
            Assert.fail("Nettoyage insert  KO");
            e.printStackTrace();
        }
        getComptabiliteDaoImpl().deleteEcritureComptable(vEcritureComptable3.getId());
    }
}
