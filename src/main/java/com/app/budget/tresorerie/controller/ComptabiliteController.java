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
    public Boolean valider(@RequestBody List<Long> ids){
        return comptabiliteService.jounaliser(ids);
    }

    @PostMapping("/annuler")
    public Boolean annuler(@RequestBody List<Long> ids){
        return comptabiliteService.annuler(ids);
    }

    @GetMapping("/all")
    public List<Comptabilite> findAll(
        @RequestParam Long idExercice,
        @RequestParam TypeJournal type, 
        @RequestParam String reference,
        @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin 
    ) {
        return comptabiliteService.findAll(idExercice,type,reference,debut,fin);
    }
    // =====================
    // 1️⃣ Journal : toutes les écritures
    // =====================
    @GetMapping("/journal")
    public List<Comptabilite> getJournal() {
        return comptabiliteService.getJournal();
    }

    // =====================
    // 2️⃣ Grand Livre : lignes regroupées par compte
    // =====================
    @GetMapping("/grand-livre")
    public Map<PlanComptable, List<LigneComptable>> getGrandLivre() {
        return comptabiliteService.getGrandLivre();
    }

    // =====================
    // 3️⃣ Balance : total débit/crédit par compte
    // =====================
    @GetMapping("/balance")
    public Map<PlanComptable, BalanceCompte> getBalance() {
        return comptabiliteService.getBalance();
    }

    // =====================
    // 4️⃣ Compte de résultat : charges, produits et résultat net
    // =====================
    @GetMapping("/compte-resultat")
    public CompteResultat getCompteResultat() {
        return comptabiliteService.getCompteResultat();
    }

}