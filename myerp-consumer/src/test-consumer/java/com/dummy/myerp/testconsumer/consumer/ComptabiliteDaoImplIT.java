package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

}
