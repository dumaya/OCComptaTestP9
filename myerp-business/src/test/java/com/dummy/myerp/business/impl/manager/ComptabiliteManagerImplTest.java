package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import static org.assertj.core.api.Assertions.assertThat;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();



    /** RG2 : Test d'equilibre somme credit = somme debit KO*/
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2KO() throws Exception {
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
                new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    /** RG2 : Test d'equilibre somme credit = somme debit OK */
    @Test
    public void checkEcritureComptableUnitRG2OK() {
        try {
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
            manager.checkEcritureComptableUnit(vEcritureComptable);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    /** RG3 : Test de validation de contrainte : au moins 2 lignes dans une écriture comptable (contrainte modèle) */
    @Test
    public void checkEcritureComptableNbLigneRG3()  {
        try {
            EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(123)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
            Assert.fail();
        }
        catch (FunctionalException e) {
            assertThat(e.getMessage()).isEqualTo("L'écriture comptable ne respecte pas les règles de gestion.");
        } catch (Exception ex) {
            Assert.fail();
        }
    }
    /** RG3 : Test de validation de contrainte : au moins 2 lignes dans une écriture comptable (test dans la méthode check)*/
    @Test
    public void checkEcritureComptableNbLigne2RG3()  {
        try {
            EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(123)));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                    null, null,
                    new BigDecimal(-123)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
            Assert.fail();
        }
        catch (FunctionalException e) {
            assertThat(e.getMessage()).isEqualTo("L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        } catch (Exception ex) {
            Assert.fail();
        }
    }
    /** RG3 : Test de validation de contrainte : au moins 2 lignes dans une écriture comptable : 1 au credit 1 au débit */
    @Test
    public void checkEcritureComptable1debit1CreditRG3() {
        try { EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(-123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
        Assert.fail();
        }
        catch (FunctionalException e) {
            assertThat(e.getMessage()).isEqualTo("L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        } catch (Exception ex) {
            Assert.fail();
        }
    }
    /** RG4 : Test de validation de contrainte : les chiffres peuvent être négatifs */
    @Test
    public void checkLigneEcritureChiffreNegatifRG4() {
        try { EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(-123),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(-123)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        }
        catch (Exception ex) {
            Assert.fail();
        }
    }

    /** RG7 : Test de validation de contrainte : pas plus de 2 chiffres aprés la virgule */
    @Test
    public void checkLigneEcriture2ChiffresRG7() {
        try { EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123.125),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(123.125)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
            Assert.fail();
        }
        catch (FunctionalException e) {
            assertThat(e.getMessage()).isEqualTo("L'écriture comptable ne respecte pas les règles de gestion.");
        } catch (Exception ex) {
            Assert.fail();
        }
    }

}
