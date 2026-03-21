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

    private final ComptabiliteRepository repository;

    public ComptabiliteService(ComptabiliteRepository repository) {
        this.repository = repository;
    }

    // =========================
    // DTOs
    // =========================

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

        public CompteResultat(BigDecimal charges, BigDecimal produits, BigDecimal resultat) {
            this.totalCharges = charges;
            this.totalProduits = produits;
            this.resultat = resultat;
        }

        public BigDecimal getTotalCharges() { return totalCharges; }
        public BigDecimal getTotalProduits() { return totalProduits; }
        public BigDecimal getResultat() { return resultat; }
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

    // =========================
    // VALIDATION (IMPORTANT)
    // =========================

public Boolean validerEcriture(List<LigneComptable> lignes) {

    BigDecimal totalDebit = lignes.stream()
            .map(l -> l.getDebit() == null ? BigDecimal.ZERO : l.getDebit())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalCredit = lignes.stream()
            .map(l -> l.getCredit() == null ? BigDecimal.ZERO : l.getCredit())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (totalDebit.compareTo(totalCredit) != 0) {
        throw new RuntimeException("❌ Ecriture non équilibrée !");
    }

    for (LigneComptable l : lignes) {

        BigDecimal debit = l.getDebit() == null ? BigDecimal.ZERO : l.getDebit();
        BigDecimal credit = l.getCredit() == null ? BigDecimal.ZERO : l.getCredit();

        if (debit.compareTo(BigDecimal.ZERO) > 0 &&
            credit.compareTo(BigDecimal.ZERO) > 0) {

            throw new RuntimeException("❌ Une ligne ne peut pas avoir débit ET crédit !");
        }

        if (debit.compareTo(BigDecimal.ZERO) == 0 &&
            credit.compareTo(BigDecimal.ZERO) == 0) {

            throw new RuntimeException("❌ Ligne vide !");
        }
    }

    return true;
}
    // =========================
    // JOURNAL
    // =========================

    public List<Comptabilite> getJournal(
            Long exercice,
            TypeJournal type,
            Long banque,
            OffsetDateTime debut,
            OffsetDateTime fin) {

        return repository.findComptabilite(exercice, type, banque, debut, fin)
                .stream()
                .sorted(Comparator.comparing(Comptabilite::getDate))
                .collect(Collectors.toList());
    }

    // =========================
    // GRAND LIVRE (PROPRE)
    // =========================

    public Map<String, List<LigneComptable>> getGrandLivre(
            Long exercice, TypeJournal type, Long banque,
            OffsetDateTime debut, OffsetDateTime fin) {

        List<Comptabilite> ecritures =
                repository.findComptabiliteGrandLivre(
                        exercice, type, banque, null, null, debut, fin);

        List<LigneComptable> lignes = ecritures.stream()
                .flatMap(e -> e.getLignes().stream())
                .collect(Collectors.toList());

        return lignes.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getCompte().getNumero() + " - " + l.getCompte().getLibelle()
                ));
    }

    // =========================
    // GRAND LIVRE ORIGINAL (INTERNE)
    // =========================

    private Map<PlanComptable, List<LigneComptable>> getGrandLivreOriginal(
            Long exercice, TypeJournal type, Long banque,
            OffsetDateTime debut, OffsetDateTime fin) {

        List<Comptabilite> ecritures =
                repository.findComptabiliteGrandLivre(
                        exercice, type, banque, null, null, debut, fin);

        List<LigneComptable> lignes = ecritures.stream()
                .flatMap(e -> e.getLignes().stream())
                .collect(Collectors.toList());

        return lignes.stream()
                .collect(Collectors.groupingBy(LigneComptable::getCompte));
    }

    // =========================
    // BALANCE (UTILISABLE API)
    // =========================

    public Map<String, BalanceCompte> getBalance(
            Long exercice, TypeJournal type, Long banque,
            OffsetDateTime debut, OffsetDateTime fin) {

        Map<String, List<LigneComptable>> grandLivre =
                getGrandLivre(exercice, type, banque, debut, fin);

        Map<String, BalanceCompte> balance = new HashMap<>();

        grandLivre.forEach((compte, lignes) -> {

            BigDecimal debit = lignes.stream()
                    .map(l -> l.getDebit() == null ? BigDecimal.ZERO : l.getDebit())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal credit = lignes.stream()
                    .map(l -> l.getCredit() == null ? BigDecimal.ZERO : l.getCredit())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            balance.put(compte, new BalanceCompte(null, debit, credit));
        });

        return balance;
    }

    // =========================
    // BALANCE INTERNE (POUR BILAN & RESULTAT)
    // =========================

    private Map<PlanComptable, BalanceCompte> getBalanceOriginal(
            Long exercice, TypeJournal type, Long banque,
            OffsetDateTime debut, OffsetDateTime fin) {

        Map<PlanComptable, List<LigneComptable>> grandLivre =
                getGrandLivreOriginal(exercice, type, banque, debut, fin);

        Map<PlanComptable, BalanceCompte> balance = new HashMap<>();

        grandLivre.forEach((compte, lignes) -> {

            BigDecimal debit = lignes.stream()
                    .map(l -> l.getDebit() == null ? BigDecimal.ZERO : l.getDebit())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal credit = lignes.stream()
                    .map(l -> l.getCredit() == null ? BigDecimal.ZERO : l.getCredit())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            balance.put(compte, new BalanceCompte(compte, debit, credit));
        });

        return balance;
    }

    // =========================
    // COMPTE DE RESULTAT (CORRIGÉ)
    // =========================

    public CompteResultat getCompteResultat(
            Long exercice, TypeJournal type, Long banque,
            OffsetDateTime debut, OffsetDateTime fin) {

        Map<PlanComptable, BalanceCompte> balance =
                getBalanceOriginal(exercice, type, banque, debut, fin);

        BigDecimal charges = BigDecimal.ZERO;
        BigDecimal produits = BigDecimal.ZERO;

        for (Map.Entry<PlanComptable, BalanceCompte> entry : balance.entrySet()) {

            String numero = entry.getKey().getNumero();
            BalanceCompte bal = entry.getValue();

            if (numero.startsWith("6")) {
                charges = charges.add(bal.getDebit().subtract(bal.getCredit()));
            } else if (numero.startsWith("7")) {
                produits = produits.add(bal.getCredit().subtract(bal.getDebit()));
            }
        }

        BigDecimal resultat = produits.subtract(charges);

        return new CompteResultat(charges, produits, resultat);
    }

    // =========================
    // BILAN (CORRIGÉ)
    // =========================

    public Bilan getBilan(
            Long exercice,
            TypeJournal type,
            Long banque,
            OffsetDateTime debut,
            OffsetDateTime fin) {

        Map<PlanComptable, BalanceCompte> balance =
                getBalanceOriginal(exercice, type, banque, debut, fin);

        BigDecimal totalActif = BigDecimal.ZERO;
        BigDecimal totalPassif = BigDecimal.ZERO;

        List<BalanceCompte> actifs = new ArrayList<>();
        List<BalanceCompte> passifs = new ArrayList<>();

        for (Map.Entry<PlanComptable, BalanceCompte> entry : balance.entrySet()) {

            PlanComptable compte = entry.getKey();
            BalanceCompte bal = entry.getValue();

            BigDecimal solde = bal.getDebit().subtract(bal.getCredit());

            if ("ACTIF".equalsIgnoreCase(compte.getSens())) {

                totalActif = totalActif.add(solde.abs());
                actifs.add(bal);

            } else if ("PASSIF".equalsIgnoreCase(compte.getSens())) {

                totalPassif = totalPassif.add(solde.abs());
                passifs.add(bal);
            }
        }

        return new Bilan(actifs, passifs, totalActif, totalPassif);
    }

    
    public boolean jounaliser(List<Long> ids) {
        ids.forEach(v->{
          Optional<Comptabilite> c = repository.findById(v);
          if (c.isPresent()) {
            c.get().setType(TypeJournal.JOURNAL);
            repository.save(c.get());
          }
        });
        return true;
    }

     public boolean annuler(List<Long> ids) {
        ids.forEach(v->{
          Optional<Comptabilite> c = repository.findById(v);
          if (c.isPresent()) {
            c.get().setType(TypeJournal.ANNULER);
            repository.save(c.get());
          }
        });
        return true;
    }
}
