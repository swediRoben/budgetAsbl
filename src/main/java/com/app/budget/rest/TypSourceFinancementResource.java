package com.app.budget.rest;

import com.app.budget.model.TypSourceFinancementDTO;
import com.app.budget.service.TypSourceFinancementService;
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
@RequestMapping(value = "/api/typSourceFinancements", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypSourceFinancementResource {

    private final TypSourceFinancementService typSourceFinancementService;

    public TypSourceFinancementResource(
            final TypSourceFinancementService typSourceFinancementService) {
        this.typSourceFinancementService = typSourceFinancementService;
    }

    @GetMapping
    public ResponseEntity<List<TypSourceFinancementDTO>> getAllTypSourceFinancements() {
        return ResponseEntity.ok(typSourceFinancementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypSourceFinancementDTO> getTypSourceFinancement(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(typSourceFinancementService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTypSourceFinancement(
            @RequestBody @Valid final TypSourceFinancementDTO typSourceFinancementDTO) {
        final Long createdId = typSourceFinancementService.create(typSourceFinancementDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTypSourceFinancement(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TypSourceFinancementDTO typSourceFinancementDTO) {
        typSourceFinancementService.update(id, typSourceFinancementDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTypSourceFinancement(
            @PathVariable(name = "id") final Long id) {
        typSourceFinancementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
