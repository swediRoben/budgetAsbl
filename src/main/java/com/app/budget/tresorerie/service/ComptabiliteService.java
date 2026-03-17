package com.app.budget.tresorerie.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.budget.constate.TypeJournal;
import com.app.budget.domain.PlanComptable;
import com.app.budget.tresorerie.entity.Comptabilite;
import com.app.budget.tresorerie.entity.LigneComptable;
import com.app.budget.tresorerie.repository.ComptabiliteRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComptabiliteService {

    private final ComptabiliteRepository comptabiliteRepository;

    public ComptabiliteService(ComptabiliteRepository comptabiliteRepository) {
        this.comptabiliteRepository = comptabiliteRepository;
    }

public static class Bilan {

    private List<BalanceCompte> actifs;
    private List<BalanceCompte> passifs;

    private BigDecimal totalActif;
    private BigDecimal totalPassif;

    public Bilan(List<BalanceCompte> actifs, List<BalanceCompte> passifs,
                 BigDecimal totalActif, BigDecimal totalPassif) {

        this.actifs = actifs;
        this.passifs = passifs;
        this.totalActif = totalActif;
        this.totalPassif = totalPassif;
    }

    public List<BalanceCompte> getActifs() { return actifs; }

    public List<BalanceCompte> getPassifs() { return passifs; }

    public BigDecimal getTotalActif() { return totalActif; }

    public BigDecimal getTotalPassif() { return totalPassif; }
}

    // =====================
    // Classes internes utiles
    // =====================
    public static class BalanceCompte {
        private PlanComptable compte;
        private BigDecimal debit;
        private BigDecimal credit;

        public BalanceCompte(PlanComptable compte, BigDecimal debit, BigDecimal credit) {
            this.compte = compte;
            this.debit = debit;
            this.credit = credit;
        }

        public PlanComptable getCompte() { return compte; }
        public BigDecimal getDebit() { return debit; }
        public BigDecimal getCredit() { return credit; }
    }

    public static class CompteResultat {
        private BigDecimal totalCharges;
        private BigDecimal totalProduits;
        private BigDecimal resultat;

        public CompteResultat(BigDecimal totalCharges, BigDecimal totalProduits, BigDecimal resultat) {
            this.totalCharges = totalCharges;
            this.totalProduits = totalProduits;
            this.resultat = resultat;
        }

        public BigDecimal getTotalCharges() { return totalCharges; }
        public BigDecimal getTotalProduits() { return totalProduits; }
        public BigDecimal getResultat() { return resultat; }
    }

    public boolean jounaliser(List<Long> ids) {
        ids.forEach(v->{
          Optional<Comptabilite> c = comptabiliteRepository.findById(v);
          if (c.isPresent()) {
            c.get().setType(TypeJournal.JOURNAL);
            comptabiliteRepository.save(c.get());
          }
        });
        return true;
    }

     public boolean annuler(List<Long> ids) {
        ids.forEach(v->{
          Optional<Comptabilite> c = comptabiliteRepository.findById(v);
          if (c.isPresent()) {
            c.get().setType(TypeJournal.ANNULER);
            comptabiliteRepository.save(c.get());
          }
        });
        return true;
    }

     public List<Comptabilite> getJournal(Long exercice, TypeJournal type, Long banque, OffsetDateTime debut,
            OffsetDateTime fin) {
          List<Comptabilite> ecritures = comptabiliteRepository.findComptabilite(exercice,type,banque,debut,fin);
          return ecritures;
     }  

     public CompteResultat getCompteResultat(Long exercice, TypeJournal type, Long banque, OffsetDateTime debut,
            OffsetDateTime fin) {
         Map<PlanComptable, BalanceCompte> balance = getBalance(exercice,type,banque, debut, fin);

        BigDecimal totalCharges = BigDecimal.ZERO;
        BigDecimal totalProduits = BigDecimal.ZERO;

        for (Map.Entry<PlanComptable, BalanceCompte> entry : balance.entrySet()) { 
            BalanceCompte bal = entry.getValue(); 
            if (entry.getKey().getClasse()!=null && entry.getKey().getClasse().getType().equals("Dépense")) {
                totalCharges = totalCharges.add(bal.getDebit().subtract(bal.getCredit()));
            } else if (entry.getKey().getClasse()!=null && entry.getKey().getClasse().getType().equals("Recette")) {
                totalProduits = totalProduits.add(bal.getCredit().subtract(bal.getDebit()));
            }
        }

        BigDecimal resultat = totalProduits.subtract(totalCharges);
        return new CompteResultat(totalCharges, totalProduits, resultat);
     }

     public Bilan getBilan(Long exercice, TypeJournal type, Long banque, OffsetDateTime debut, OffsetDateTime fin) {
         Map<PlanComptable, BalanceCompte> balance = getBalance(exercice,type,banque, debut, fin);

    BigDecimal totalActif = BigDecimal.ZERO;
    BigDecimal totalPassif = BigDecimal.ZERO;

    List<BalanceCompte> actifs = new ArrayList<>();
    List<BalanceCompte> passifs = new ArrayList<>();

    for (Map.Entry<PlanComptable, BalanceCompte> entry : balance.entrySet()) {

        PlanComptable compte = entry.getKey();
        BalanceCompte bal = entry.getValue();

        BigDecimal solde = bal.getDebit().subtract(bal.getCredit());

        if (compte.getSens() != null && compte.getSens().equals("ACTIF")) {

            totalActif = totalActif.add(solde.abs());
            actifs.add(bal);

        } else {

            totalPassif = totalPassif.add(solde.abs());
            passifs.add(bal);

        }
    }

    return new Bilan(actifs, passifs, totalActif, totalPassif);
     }

     public Map<PlanComptable, BalanceCompte> getBalance(Long exercice, TypeJournal type, Long banque,
            OffsetDateTime debut, OffsetDateTime fin) {
          Map<PlanComptable, List<LigneComptable>> grandLivre = getGrandLivre(exercice,type,banque,null, null, debut, fin);
        Map<PlanComptable, BalanceCompte> balance = new HashMap<>();

        grandLivre.forEach((compte, lignes) -> {
            BigDecimal totalDebit = lignes.stream()
                    .map(l -> l.getDebit() != null ? l.getDebit() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalCredit = lignes.stream()
                    .map(l -> l.getCredit() != null ? l.getCredit() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            balance.put(compte, new BalanceCompte(compte, totalDebit, totalCredit));
        });

        return balance;
     }

     public Map<PlanComptable, List<LigneComptable>> getGrandLivre(Long exercice, TypeJournal type, Long banque,
            String compteDebut, String compteFin, OffsetDateTime debut, OffsetDateTime fin) {
            List<Comptabilite> ecritures = comptabiliteRepository.findComptabiliteGrandLivre(exercice,type,banque,compteDebut,compteFin,debut,fin);
          List<LigneComptable> allLignes = ecritures.stream()
                .flatMap(e -> e.getLignes().stream())
                .collect(Collectors.toList());

        // regroupe par compte
        return allLignes.stream()
                .collect(Collectors.groupingBy(LigneComptable::getCompte));
    }

   

}
