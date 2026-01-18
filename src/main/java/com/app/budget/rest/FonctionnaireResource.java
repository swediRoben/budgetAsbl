package com.app.budget.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.budget.domain.Fonctionnaire; 
import com.app.budget.service.FonctionnaireService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/fonctionnaire", produces = MediaType.APPLICATION_JSON_VALUE)
public class FonctionnaireResource {
     private final FonctionnaireService service;

    public FonctionnaireResource(FonctionnaireService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Fonctionnaire>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

      @GetMapping("/responsables")
    public ResponseEntity<List<Fonctionnaire>> getAllResponsables() {
        return ResponseEntity.ok(service.findResponsable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fonctionnaire> getById(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Object> createExercice(@RequestBody @Valid final Fonctionnaire fonctionnaire) {
        final Fonctionnaire createdId = service.save(fonctionnaire);
        if (createdId!=null) {
           return new ResponseEntity<>(createdId, HttpStatus.CREATED); 
        }else{
            return new ResponseEntity<>(createdId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateExercice(@PathVariable(name = "id") final Long id,
           @RequestBody @Valid final Fonctionnaire fonctionnaire) {
        final Fonctionnaire createdId = service.updata(id,fonctionnaire);
        if (createdId!=null) {
           return new ResponseEntity<>(createdId, HttpStatus.CREATED); 
        }else{
            return new ResponseEntity<>(createdId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteExercice(@PathVariable(name = "id") final Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
