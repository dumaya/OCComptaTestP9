package com.dummy.myerp.consumer;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompteComptableDaoCacheTest {

    @Mock
    ComptabiliteDao comptabiliteDao;

    //@Test
    public void getByNumero() {
        // GIVEN
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
        when(comptabiliteDao.getListCompteComptable()).thenReturn(listeComptes);
        CompteComptableDaoCache compteDaoCache = new CompteComptableDaoCache();

        // WHEN
        List<CompteComptable> listeComptestest = ConsumerHelper.getDaoProxy().getComptabiliteDao().getListCompteComptable();
        CompteComptable compteCherche = compteDaoCache.getByNumero(2);
        // THEN
        assertThat(compteCherche.getNumero().intValue()).isEqualTo(2);

    }

}
