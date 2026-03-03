package com.app.budget.tresorerie.controller;

import java.time.OffsetDateTime;
import java.util.List;
 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.app.budget.tresorerie.dto.JournalTresorerieDto;
import com.app.budget.tresorerie.service.JournalTresorerieService;

import io.swagger.v3.oas.annotations.parameters.RequestBody; 
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/journal-tresorerie")
@RequiredArgsConstructor 
public class JournalTresorerieController {

    private final JournalTresorerieService service;

    // ================= CREATE =================
    @PostMapping
    public JournalTresorerieDto create(@RequestBody JournalTresorerieDto dto) {
        return service.create(dto);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public JournalTresorerieDto update(
            @PathVariable Long id,
              @RequestBody JournalTresorerieDto dto) {
        return service.update(id, dto);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public JournalTresorerieDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ================= FIND ALL =================
    @GetMapping
    public List<JournalTresorerieDto> findAll(
            @RequestParam(required = false) Long exerciceId,
            @RequestParam(required = false) Long banqueId,
            @RequestParam(required = false) Long compteBancaireId,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) OffsetDateTime debut,
            @RequestParam(required = false) OffsetDateTime fin) {

        return service.findAll(
                exerciceId,
                banqueId,
                compteBancaireId,
                numero,
                debut,
                fin);
    }
}