package com.app.budget.tresorerie.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.app.budget.tresorerie.dto.JournalTresorerieDto;
import com.app.budget.tresorerie.dto.JournalTresorerieFilter;
import com.app.budget.tresorerie.entity.JournalTresorerie;
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

         @PostMapping("/search")
    public Page<JournalTresorerie> search(
            @RequestBody JournalTresorerieFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return service.search(filter, pageable);
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