package com.app.budget.structure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.budget.structure.entity.Structure;
import com.app.budget.structure.service.StructureService;

import java.util.List;

@RestController
@RequestMapping("/api/structure")
@CrossOrigin("*")
public class StructureController {

    @Autowired
    private StructureService service;

    // CREATE
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Structure> create(
            @RequestParam("email") String email,
            @RequestParam("reseausocial") String reseausocial,
            @RequestParam("telephone") String telephone,
            @RequestParam("adresse") String adresse,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws Exception {

        Structure s = new Structure();
        s.setEmail(email);
        s.setReseausocial(reseausocial);
        s.setTelephone(telephone);
        s.setAdresse(adresse);

        return ResponseEntity.ok(service.save(s, file));
    }

    
    // GET ALL
    @GetMapping
    public List<Structure> getAll() {
        return service.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public Structure getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public Structure update(
            @PathVariable Long id,
            @RequestParam("email") String email,
            @RequestParam("reseausocial") String reseausocial,
            @RequestParam("telephone") String telephone,
            @RequestParam("adresse") String adresse,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws Exception {

        Structure s = new Structure();
        s.setEmail(email);
        s.setReseausocial(reseausocial);
        s.setTelephone(telephone);
        s.setAdresse(adresse);

        return service.update(id, s, file);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}