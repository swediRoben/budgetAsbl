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

    // =====================
    // 1️⃣ Journal : toutes les écritures
    // =====================
    public List<Comptabilite> getJournal() {
          List<Comptabilite> ecritures = comptabiliteRepository.findAllWithLignes();
          return ecritures;
    }

    // =====================
    // 2️⃣ Grand Livre : regroupé par compte
    // =====================
    public Map<PlanComptable, List<LigneComptable>> getGrandLivre() {
        List<Comptabilite> ecritures = comptabiliteRepository.findAll();
        List<LigneComptable> allLignes = ecritures.stream()
                .flatMap(e -> e.getLignes().stream())
                .collect(Collectors.toList());

        // regroupe par compte
        return allLignes.stream()
                .collect(Collectors.groupingBy(LigneComptable::getCompte));
    }

    // =====================
    // 3️⃣ Balance : total débit/crédit par compte
    // =====================
    public Map<PlanComptable, BalanceCompte> getBalance() {
        Map<PlanComptable, List<LigneComptable>> grandLivre = getGrandLivre();
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

    // =====================
    // 4️⃣ Compte de résultat : charges et produits
    // =====================
    public CompteResultat getCompteResultat() {
        Map<PlanComptable, BalanceCompte> balance = getBalance();

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

     public List<Comptabilite> findAll(Long idExercice, TypeJournal type, String reference, OffsetDateTime debut,
            OffsetDateTime fin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
     }

}
