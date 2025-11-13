package com.app.budget.rest;

import com.app.budget.model.CategorieDTO;
import com.app.budget.service.CategorieService;
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
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategorieResource {

    private final CategorieService categorieService;

    public CategorieResource(final CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorie(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(categorieService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCategorie(
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        final Long createdId = categorieService.create(categorieDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCategorie(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        categorieService.update(id, categorieDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategorie(@PathVariable(name = "id") final Long id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
