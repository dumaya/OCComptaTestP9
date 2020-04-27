package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplIT extends BusinessTestCase {

    /** RG6 : La référence d'une écriture comptable doit être unique, il n'est pas possible de créer plusieurs écritures ayant la même référence. */
     @Test
    public void checkRefUniqueEcritureReferenceRG6() {
         SpringRegistry.init();
         try { EcritureComptable vEcritureComptable;
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
        catch (Exception ex) {
            Assert.fail();
        }
    }
}
