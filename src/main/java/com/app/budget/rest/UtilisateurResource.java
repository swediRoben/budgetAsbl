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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 
import com.app.budget.domain.Utilisateur; 
import com.app.budget.service.UtilisateurService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurResource {
     private final UtilisateurService service;

    public UtilisateurResource(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/login")
    public Object getAllResponsables(@PathVariable(name = "email") final String email,@RequestParam(name = "password") final String password) {
        Utilisateur data=service.findByEmail(email);
        if (data!=null && data.getPassword()==password) {
            data.setPassword(password);
         }
       return data;
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Object> createExercice(@RequestBody @Valid final Utilisateur data) {
        final Utilisateur createdId = service.save(data);
        if (createdId!=null) {
           return new ResponseEntity<>(createdId, HttpStatus.CREATED); 
        }else{
            return new ResponseEntity<>(createdId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateExercice(@PathVariable(name = "id") final Long id,
           @RequestBody @Valid final Utilisateur data) {
        final Utilisateur createdId = service.updata(id,data);
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
