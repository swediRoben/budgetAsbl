package com.app.budget.rest;

import com.app.budget.model.BeneficiaireDTO;
import com.app.budget.service.BeneficiaireService;
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
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping(value = "/api/beneficiaires", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BeneficiaireResource {

    private final BeneficiaireService beneficiaireService;

    public BeneficiaireResource(final BeneficiaireService beneficiaireService) {
        this.beneficiaireService = beneficiaireService;
    }

    @GetMapping
    public ResponseEntity<List<BeneficiaireDTO>> getAllBeneficiaires() {
        return ResponseEntity.ok(beneficiaireService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaireDTO> getBeneficiaire(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(beneficiaireService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBeneficiaire(
            @RequestBody @Valid final BeneficiaireDTO beneficiaireDTO) {
        final Long createdId = beneficiaireService.create(beneficiaireDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBeneficiaire(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final BeneficiaireDTO beneficiaireDTO) {
        beneficiaireService.update(id, beneficiaireDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBeneficiaire(@PathVariable(name = "id") final Long id) {
        beneficiaireService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
