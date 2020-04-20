package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CompteComptableTest {

    @Test
    public void getByNumeroOK() {
        CompteComptable compte1 = new CompteComptable();
        compte1.setLibelle("Compte 1");
        compte1.setNumero(1);
        CompteComptable compte2 = new CompteComptable();
        compte2.setLibelle("Compte 2");
        compte2.setNumero(2);
        CompteComptable compte3 = new CompteComptable();
        compte3.setLibelle("Compte 3");
        compte3.setNumero(3);
        List<CompteComptable> listeComptes = new ArrayList<>();
        listeComptes.add(compte1);
        listeComptes.add(compte2);
        listeComptes.add(compte3);
        CompteComptable compteCherche = CompteComptable.getByNumero(listeComptes,1);
        Assert.assertEquals(1,compteCherche.getNumero().intValue());
    }
    @Test
    public void getByNumeroNonTrouvé() {
        CompteComptable compte1 = new CompteComptable();
        compte1.setLibelle("Compte 1");
        compte1.setNumero(1);
        CompteComptable compte2 = new CompteComptable();
        compte2.setLibelle("Compte 2");
        compte2.setNumero(2);
        CompteComptable compte3 = new CompteComptable();
        compte3.setLibelle("Compte 3");
        compte3.setNumero(3);
        List<CompteComptable> listeComptes = new ArrayList<>();
        listeComptes.add(compte1);
        listeComptes.add(compte2);
        listeComptes.add(compte3);
        CompteComptable compteCherche = CompteComptable.getByNumero(listeComptes,0);
        Assert.assertEquals(null, compteCherche);
    }

}
