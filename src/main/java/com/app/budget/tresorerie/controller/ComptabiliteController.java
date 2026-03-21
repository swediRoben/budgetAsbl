package com.app.budget.tresorerie.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

    private final ComptabiliteService service;

    public ComptabiliteController(ComptabiliteService service) {
        this.service = service;
    }

    // =====================
    // VALIDATION
    // =====================

    @PostMapping("/valider")
    public ResponseEntity<Boolean> valider(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(service.jounaliser(ids));
    }

    @PostMapping("/annuler")
    public ResponseEntity<Boolean> annuler(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(service.annuler(ids));
    }

    @PostMapping("/valider-ecriture")
public ResponseEntity<?> validerEcriture(@RequestBody List<LigneComptable> lignes) {

    try {
        Boolean result = service.validerEcriture(lignes);
        return ResponseEntity.ok(result);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    // =====================
    // 1️⃣ JOURNAL
    // =====================

    @GetMapping("/journal")
    public ResponseEntity<List<Comptabilite>> getJournal(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {

        return ResponseEntity.ok(
                service.getJournal(exercice, type, banque, debut, fin)
        );
    }

    // =====================
    // 2️⃣ GRAND LIVRE (CORRIGÉ)
    // =====================

    @GetMapping("/grand-livre")
    public ResponseEntity<Map<String, List<LigneComptable>>> getGrandLivre(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {

        return ResponseEntity.ok(
                service.getGrandLivre(exercice, type, banque, debut, fin)
        );
    }

    // =====================
    // 3️⃣ BALANCE (CORRIGÉ)
    // =====================

    @GetMapping("/balance")
    public ResponseEntity<Map<String, ComptabiliteService.BalanceCompte>> getBalance(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {

        return ResponseEntity.ok(
                service.getBalance(exercice, type, banque, debut, fin)
        );
    }

    // =====================
    // 4️⃣ BILAN
    // =====================

    @GetMapping("/bilan")
    public ResponseEntity<ComptabiliteService.Bilan> getBilan(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {

        return ResponseEntity.ok(
                service.getBilan(exercice, type, banque, debut, fin)
        );
    }

    // =====================
    // 5️⃣ COMPTE DE RESULTAT
    // =====================

    @GetMapping("/compte-resultat")
    public ResponseEntity<ComptabiliteService.CompteResultat> getCompteResultat(
            @RequestParam(required = false) Long exercice,
            @RequestParam(required = false) Long banque,
            @RequestParam(required = false) TypeJournal type,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {

        return ResponseEntity.ok(
                service.getCompteResultat(exercice, type, banque, debut, fin)
        );
    }
}