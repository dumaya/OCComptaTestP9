package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CompteComptableRMTest {
    //@Test
    public void mapRowTest() {
        CompteComptable compte = new CompteComptable(1,"premier compte");
        CompteComptableRM compteRM = new CompteComptableRM();


        Assert.assertEquals(1,compte.getNumero().intValue());
        Assert.assertEquals("premier compte",compte.getLibelle());
        Assert.assertEquals("CompteComptable{numero=1, libelle='premier compte'}",compte.toString());
    }


}
