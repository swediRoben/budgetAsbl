package com.app.budget.tresorerie.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.budget.constate.TypeClasse;
import com.app.budget.tresorerie.dto.BanqueDTO;
import com.app.budget.tresorerie.entity.OperationComptable; 
import com.app.budget.tresorerie.service.OperationComptableService;

@RestController
@RequestMapping("/api/operationcomptable")
@CrossOrigin("*")
public class OperationComptableController {
    private final OperationComptableService service;

    
    public OperationComptableController(OperationComptableService service) {
        this.service = service;
    }

    @PostMapping
    public OperationComptable create(@RequestBody OperationComptable dto){
        return service.create(dto);
    }

    @GetMapping
    public List<OperationComptable> getAll(
            @RequestParam(required = false) TypeClasse type) {
        return service.getAll(type);
    }

    @GetMapping("/{id}")
    public OperationComptable getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public OperationComptable update(@PathVariable Long id, @RequestBody OperationComptable dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
