package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;



public class LigneEcritureComptableTest {

    @Test
    public void getterSetter() {
        LigneEcritureComptable ligne = new LigneEcritureComptable();
        ligne.setCredit(new BigDecimal(1));
        ligne.setDebit(new BigDecimal(2));

        ligne.setCompteComptable(new CompteComptable(3,"test"));
        ligne.setLibelle("test ligne");

        Assert.assertEquals(new BigDecimal(1),ligne.getCredit());
        Assert.assertEquals("test ligne",ligne.getLibelle());
        Assert.assertEquals(new BigDecimal(2),ligne.getDebit());
        Assert.assertEquals("test", ligne.getCompteComptable().getLibelle());

        Assert.assertEquals("LigneEcritureComptable{compteComptable=CompteComptable{numero=3, libelle='test'}, libelle='test ligne', debit=2, credit=1}",ligne.toString());
    }

}
