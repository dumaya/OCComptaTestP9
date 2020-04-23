package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void getterSetter() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("test getset");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "33"));
        vEcriture.setId(1);
        vEcriture.setDate(new Date(1587543661));
        JournalComptable journal = new JournalComptable();
        journal.setCode("codeJournal");
        journal.setLibelle("journalC");
        vEcriture.setJournal(journal);
        vEcriture.setReference("reference");

        Assert.assertEquals(1,vEcriture.getId().intValue());
        Assert.assertEquals("test getset",vEcriture.getLibelle());
        Assert.assertEquals(new Date(1587543661),vEcriture.getDate());
        Assert.assertEquals("journalC", vEcriture.getJournal().getLibelle());
        Assert.assertEquals("reference",vEcriture.getReference());

        Assert.assertEquals("EcritureComptable{id=1, journal=JournalComptable{code='codeJournal', libelle='journalC'}, reference='reference', date=Mon Jan 19 09:59:03 CET 1970, libelle='test getset', totalDebit=0, totalCredit=33, listLigneEcriture=[\n" +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='0', debit=null, credit=null}\n" +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='-33', debit=null, credit=33}\n" +
                "]}",vEcriture.toString());
    }

    @Test
    public void getTotalDebitNullNull() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("null+null");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "33"));
        Assert.assertEquals(0, vEcriture.getTotalDebit().compareTo(new BigDecimal(0)));
    }

    @Test
    public void getTotalDebitOK() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("getTotalDebitOK");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1040", "7"));
        Assert.assertEquals(0, vEcriture.getTotalDebit().compareTo(new BigDecimal(1341)));
    }

    @Test
    public void getTotalCreditNullNull() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("null+null");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "33", null));
        Assert.assertEquals(0, vEcriture.getTotalCredit().compareTo(new BigDecimal(0)));
    }

    @Test
    public void getTotalCreditOK() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("getTotalDebitOK");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1040", null));
        Assert.assertEquals(0, vEcriture.getTotalCredit().compareTo(new BigDecimal(334)));
    }

    @Test
    public void isEquilibreeOK() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
    }
    @Test
    public void isEquilibreeKOdebitinferieurcredit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }
    @Test
    public void isEquilibreeKOdebitsupérieurcredit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

}
