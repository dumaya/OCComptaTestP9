package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class JournalComptableTest {

    @Test
    public void getByCodeOK() {
       JournalComptable journal1 = new JournalComptable();
        journal1.setLibelle("Journal 1");
        journal1.setCode("1");
        JournalComptable journal2 = new JournalComptable();
        journal2.setLibelle("Journal 2");
        journal2.setCode("2");
        JournalComptable journal3 = new JournalComptable();
        journal3.setLibelle("Journal 3");
        journal3.setCode("3");
        List<JournalComptable> listeJournaux = new ArrayList<>();
        listeJournaux.add(journal1);
        listeJournaux.add(journal2);
        listeJournaux.add(journal3);
        JournalComptable journalCherche = JournalComptable.getByCode(listeJournaux,"1");
        Assert.assertEquals("1",journalCherche.getCode());
    }
    @Test
    public void getByCodeNonTrouvé() {
        JournalComptable journal1 = new JournalComptable();
        journal1.setLibelle("Journal 1");
        journal1.setCode("1");
        JournalComptable journal2 = new JournalComptable();
        journal2.setLibelle("Journal 2");
        journal2.setCode("2");
        JournalComptable journal3 = new JournalComptable();
        journal3.setLibelle("Journal 3");
        journal3.setCode("3");
        List<JournalComptable> listeJournaux = new ArrayList<>();
        listeJournaux.add(journal1);
        listeJournaux.add(journal2);
        listeJournaux.add(journal3);
        JournalComptable journalCherche = JournalComptable.getByCode(listeJournaux,"4");
        Assert.assertEquals(null,journalCherche);
    }

}
