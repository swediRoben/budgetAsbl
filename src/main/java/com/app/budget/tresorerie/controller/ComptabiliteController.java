package com.app.budget.tresorerie.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.budget.constate.TypeJournal;
import com.app.budget.domain.PlanComptable;
import com.app.budget.tresorerie.entity.Comptabilite;
import com.app.budget.tresorerie.entity.LigneComptable;
import com.app.budget.tresorerie.service.ComptabiliteService;
import com.app.budget.tresorerie.service.ComptabiliteService.BalanceCompte;
import com.app.budget.tresorerie.service.ComptabiliteService.Bilan;
import com.app.budget.tresorerie.service.ComptabiliteService.CompteResultat;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/comptabilite")
@CrossOrigin("*")
public class ComptabiliteController {

    private final ComptabiliteService comptabiliteService;

    public ComptabiliteController(ComptabiliteService comptabiliteService) {
        this.comptabiliteService = comptabiliteService;
    }

    @PostMapping("/valider")
    public Boolean valider(@RequestBody List<Long> ids) {
        return comptabiliteService.jounaliser(ids);
    }

    @PostMapping("/annuler")
    public Boolean annuler(@RequestBody List<Long> ids) {
        return comptabiliteService.annuler(ids);
    }

    // =====================
    // 1️⃣ Journal : toutes les écritures
    // =====================
    @GetMapping("/journal")
    public List<Comptabilite> getJournal(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {
        return comptabiliteService.getJournal(exercice, type, banque, debut, fin);
    }

    // =====================
    // 2️⃣ Grand Livre : lignes regroupées par compte
    // =====================
    @GetMapping("/grand-livre")
    public Map<PlanComptable, List<LigneComptable>> getGrandLivre(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) String compteDebut,
            @RequestParam(required = false) String compteFin,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {
        return comptabiliteService.getGrandLivre(exercice, type, banque,compteDebut,compteFin, debut, fin);
    }

    // =====================
    // 3️⃣ Balance : total débit/crédit par compte
    // =====================
    @GetMapping("/balance")
    public Map<PlanComptable, BalanceCompte> getBalance(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {
        return comptabiliteService.getBalance(exercice, type, banque, debut, fin);
    }

    @GetMapping("/bilan")
    public Bilan getBilan(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {
        return comptabiliteService.getBilan(exercice, type, banque, debut, fin);

    }

    // =====================
    // 4️⃣ Compte de résultat : charges, produits et résultat net
    // =====================
    @GetMapping("/compte-resultat")
    public CompteResultat getCompteResultat(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {
        return comptabiliteService.getCompteResultat(exercice, type, banque, debut, fin);
    }

}