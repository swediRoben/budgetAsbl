package com.app.budget.tresorerie.controller;
import org.springframework.web.bind.annotation.*;

import com.app.budget.tresorerie.dto.CompteBancaireDto;
import com.app.budget.tresorerie.service.CompteBancaireService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/compte-comptables")
public class CompteBancaireController {

    private final CompteBancaireService compteService;

    public CompteBancaireController(CompteBancaireService compteService) {
        this.compteService = compteService;
    }

    @PostMapping
    public CompteBancaireDto create(@RequestBody CompteBancaireDto dto){
        return compteService.create(dto);
    }

    @GetMapping
    public List<CompteBancaireDto> getAll(
            @RequestParam(required = false) Long banqueId,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) Long idDevise){
        return compteService.getAll(banqueId, numero, idDevise);
    }

    @GetMapping("/{id}")
    public CompteBancaireDto getById(@PathVariable Long id){
        return compteService.getById(id);
    }

    @PutMapping("/{id}")
    public CompteBancaireDto update(@PathVariable Long id, @RequestBody CompteBancaireDto dto){
        return compteService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        compteService.delete(id);
    }
}

