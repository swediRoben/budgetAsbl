package com.app.budget.rest;

import com.app.budget.model.ProjetDTO;
import com.app.budget.service.ProjetService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/projets", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjetResource {

    private final ProjetService projetService;

    public ProjetResource(final ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping
    public ResponseEntity<List<ProjetDTO>> getAllProjets() {
        return ResponseEntity.ok(projetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetDTO> getProjet(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(projetService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProjet(@RequestBody @Valid final ProjetDTO projetDTO) {
        final Long createdId = projetService.create(projetDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProjet(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ProjetDTO projetDTO) {
        projetService.update(id, projetDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProjet(@PathVariable(name = "id") final Long id) {
        projetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
