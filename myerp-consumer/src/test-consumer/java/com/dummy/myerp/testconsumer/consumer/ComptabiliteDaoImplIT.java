package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;
import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteDaoImplIT extends ConsumerTestCase {

    /**
     * Constructeur.
     */
    public ComptabiliteDaoImplIT() {
        super();
    }

    /** Test getListCompteComptable */
    @Test
    public void getListCompteComptableTest() {
        List<CompteComptable> vList = getComptabiliteDaoImpl().getListCompteComptable();
        assertThat(vList.get(0).getLibelle()).isNotEmpty();
    }

    /** Test getListJournalComptable */
    @Test
    public void getListJournalComptableTest() {
        List<JournalComptable> vList = getComptabiliteDaoImpl().getListJournalComptable();
        assertThat(vList.get(0).getLibelle()).isNotEmpty();
    }

    /** Test getListEcritureComptable */
    @Test
    public void getListEcritureComptableTest() {
        List<EcritureComptable> vList = getComptabiliteDaoImpl().getListEcritureComptable();
        assertThat(vList.get(0).getLibelle()).isNotEmpty();
    }

    /** Test getEcritureComptable */
    @Test
    public void getEcritureComptableTest() {
        EcritureComptable ecritureComptable;
        try {
            ecritureComptable = getComptabiliteDaoImpl().getEcritureComptable(-1);
            assertThat(ecritureComptable.getId()).isEqualTo(-1);
        } catch (NotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    /** Test getEcritureComptable non trouvée */
    @Test
    public void getEcritureComptableKOTest() {
        EcritureComptable ecritureComptable;
        try {
            ecritureComptable = getComptabiliteDaoImpl().getEcritureComptable(-10);
            Assert.fail();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
    /** Test getEcritureComptableByRef  */
    @Test
    public void getEcritureComptableByRefTest() {
        EcritureComptable ecritureComptable;
        try {
            ecritureComptable = getComptabiliteDaoImpl().getEcritureComptableByRef("VE-2016/00002");
            assertThat(ecritureComptable.getId()).isEqualTo(-2);
        } catch (NotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    /** Test getEcritureComptableByRef non trouvée */
    @Test
    public void getEcritureComptableByRefKOTest() {
        EcritureComptable ecritureComptable;
        try {
            ecritureComptable = getComptabiliteDaoImpl().getEcritureComptableByRef("OHOH");
            Assert.fail();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Test  loadListLigneEcriture  */
    @Test
    public void  loadListLigneEcritureTest() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(-2);
        getComptabiliteDaoImpl().loadListLigneEcriture(ecritureComptable);
        assertThat(ecritureComptable.getListLigneEcriture().size()).isEqualTo(3);
    }

    /** Test  insert ecriture, insert ligne ecriture, update delete de la même ecriture  */
    @Test
    public void  majEcritureTest() {
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
        getComptabiliteDaoImpl().insertEcritureComptable(vEcritureComptable);

        assertThat(vEcritureComptable.getId()).isNotNull();
        // test update
        vEcritureComptable.setLibelle("libelle maj");
        vEcritureComptable.getListLigneEcriture().get(0).setCredit(new BigDecimal(55));

        getComptabiliteDaoImpl().updateEcritureComptable(vEcritureComptable);

        assertThat(vEcritureComptable.getLibelle()).isEqualTo("libelle maj");
        assertThat(vEcritureComptable.getListLigneEcriture().get(0).getCredit()).isEqualByComparingTo(new BigDecimal(55));

        // Test delete
        getComptabiliteDaoImpl().deleteEcritureComptable(vEcritureComptable.getId());

        EcritureComptable ecritureComptable;
        try {
            ecritureComptable = getComptabiliteDaoImpl().getEcritureComptable(vEcritureComptable.getId());
            Assert.fail();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Test  selectLastSequence  */
    @Test
    public void  selectLastSequenceEcritureComptableTest() {

        try {
            Integer sequence = getComptabiliteDaoImpl().selectLastSequenceEcritureComptable("OD",2016);
            assertThat(sequence).isGreaterThanOrEqualTo(88);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

}
