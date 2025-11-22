package com.app.budget.rest;

import com.app.budget.model.ActiviteDTO;
import com.app.budget.service.ActiviteService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping(value = "/api/activites", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ActiviteResource {

    private final ActiviteService activiteService;

    public ActiviteResource(final ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

    @GetMapping
    public ResponseEntity<List<ActiviteDTO>> getAllActivites(@RequestParam(required = false) Long projet,@RequestParam(required = false) Long categorie) {
        return ResponseEntity.ok(activiteService.findAll(projet,categorie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiviteDTO> getActivite(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(activiteService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createActivite(@RequestBody @Valid final ActiviteDTO activiteDTO) {
        final Long createdId = activiteService.create(activiteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateActivite(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ActiviteDTO activiteDTO) {
        activiteService.update(id, activiteDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteActivite(@PathVariable(name = "id") final Long id) {
        activiteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
