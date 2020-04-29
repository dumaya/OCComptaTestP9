package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SequenceEcritureComptableTest {
    @Test
    public void getterSetter() {
        SequenceEcritureComptable sequence = new SequenceEcritureComptable("GK",5,6);
        Assert.assertEquals(5,sequence.getAnnee().intValue());
        Assert.assertEquals(6,sequence.getDerniereValeur().intValue());
        Assert.assertEquals("SequenceEcritureComptable{annee=5, derniereValeur=6}",sequence.toString());
    }


}
