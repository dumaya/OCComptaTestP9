package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;
import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplIT extends BusinessTestCase {

    //@BeforeAll
    //public void before() {
    //    SpringRegistry.init();
    //}
    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    /** RG6 : La référence d'une écriture comptable doit être unique, il n'est pas possible de créer plusieurs écritures ayant la même référence. */
     @Test
    public void checkRefUniqueEcritureReferenceRG6() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                null, null,
                 new BigDecimal(121)));
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);
        String ref1 = vEcritureComptable.getReference();
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);
        String ref2 = vEcritureComptable.getReference();
        assertThat(ref2).isNotEqualTo(ref1);
    }

    /** RG5 :
     * La référence d'une écriture comptable est composée du code du journal dans lequel figure l'écriture
     * suivi de l'année et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres incrémenté automatiquement à chaque écriture.
     * Le formatage de la référence est : XX-AAAA/#####.
     */
    @Test
    public void checkFormatEcritureReferenceRG5() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("OD", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                null, null,
                new BigDecimal(121)));
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);
        String ref1 = vEcritureComptable.getReference();
        Integer sequenceRef1 = Integer.valueOf(ref1.substring(9,13));
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);
        String ref2 = vEcritureComptable.getReference();
        Integer sequenceRef2 = Integer.valueOf(ref2.substring(9,13));
        assertThat(sequenceRef2).isEqualTo(sequenceRef1 + 1);
        assertThat(ref1).matches("\\S{1,2}-\\d{4}\\\\\\d{5}");
        assertThat(ref2).matches("\\S{1,2}-\\d{4}\\\\\\d{5}");
    }

    /** Test cas passant check Ecriture comptable context */
    @Test
    public void checkcheckEcritureComptableContext() {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date(TimeUnit.SECONDS.toMillis(1588164448L)));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020\\02522");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                null, null,
                new BigDecimal(121)));
        try {
            SpringRegistry.getBusinessProxy().getComptabiliteManager().checkEcritureComptable(vEcritureComptable);
        }catch ( FunctionalException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    /** Test cas non passant check Ecriture comptable context  */
    @Test
    public void checkcheckEcritureComptableContextKO() {
        try {
            EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("BQ-2016/00003");
            SpringRegistry.getBusinessProxy().getComptabiliteManager().checkEcritureComptable(vECRef);
            Assert.fail();
        } catch (FunctionalException  e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }
    /** Test cas passant add reference (ref non trouvée) */
    @Test
    public void checkAddReferenceNonTrouvé() {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date(TimeUnit.SECONDS.toMillis(1588164448L)));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                null, null,
                new BigDecimal(121)));
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);
        assertThat(vEcritureComptable.getReference()).contains("AC");
        assertThat(vEcritureComptable.getReference()).contains("2020");
    }
    /** Test cas passant add reference (ref trouvée) */
    @Test
    public void checkAddReferenceTrouvé() {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
        vEcritureComptable.setDate(new Date(TimeUnit.SECONDS.toMillis(1463229921L)));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(2)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                null, null,
                new BigDecimal(121)));
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);
        assertThat(vEcritureComptable.getReference()).contains("VE");
        assertThat(vEcritureComptable.getReference()).contains("2016");
    }
}
