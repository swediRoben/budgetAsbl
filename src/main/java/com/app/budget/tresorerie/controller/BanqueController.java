package com.app.budget.tresorerie.controller;
 
import org.springframework.web.bind.annotation.*;

import com.app.budget.tresorerie.dto.BanqueDTO;
import com.app.budget.tresorerie.service.BanqueService;

import java.util.List;

@RestController
@RequestMapping("/api/banques")
public class BanqueController {

    private final BanqueService banqueService;

    public BanqueController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @PostMapping
    public BanqueDTO create(@RequestBody BanqueDTO dto){
        return banqueService.create(dto);
    }

    @GetMapping
    public List<BanqueDTO> getAll(
            @RequestParam(required = false) String libelle,
            @RequestParam(required = false) Boolean actif) {
        return banqueService.getAll(libelle, actif);
    }

    @GetMapping("/{id}")
    public BanqueDTO getById(@PathVariable Long id){
        return banqueService.getById(id);
    }

    @PutMapping("/{id}")
    public BanqueDTO update(@PathVariable Long id, @RequestBody BanqueDTO dto){
        return banqueService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        banqueService.delete(id);
    }
}
