package com.dummy.myerp.model.bean.comptabilite;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
    /** Le code journal */
    @NotNull
    @Size(min = 2, max = 2)
    private String codeJournal;
    /** L'année */
    @Pattern(regexp = "\\d{4}")
    private Integer annee;
    /** La dernière valeur utilisée */
    @Pattern(regexp = "\\d{5}")
    private Integer derniereValeur;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructeur
     * @param pCodeJournal-
     * @param pAnnee -
     * @param pDerniereValeur -
     */
    public SequenceEcritureComptable(String pCodeJournal, Integer pAnnee, Integer pDerniereValeur) {
        codeJournal = pCodeJournal;
        annee = pAnnee;
        derniereValeur = pDerniereValeur;
    }


    // ==================== Getters/Setters ====================
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }
    public String getCodeJournal() { return codeJournal;  }
    public void setCodeJournal(String codeJournal) { this.codeJournal = codeJournal; }

    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("codeJournal=").append(codeJournal)
            .append("annee=").append(annee)
            .append(vSEP).append("derniereValeur=").append(derniereValeur)
            .append("}");
        return vStB.toString();
    }


}
