package com.app.budget.rest;

import com.app.budget.model.SourceFinacementDTO;
import com.app.budget.service.SourceFinacementService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/sourceFinancements", produces = MediaType.APPLICATION_JSON_VALUE)
public class SourceFinacementResource {

    private final SourceFinacementService sourceFinacementService;

    public SourceFinacementResource(final SourceFinacementService sourceFinacementService) {
        this.sourceFinacementService = sourceFinacementService;
    }

    @GetMapping
    public ResponseEntity<List<SourceFinacementDTO>> getAllSourceFinacements() {
        return ResponseEntity.ok(sourceFinacementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SourceFinacementDTO> getSourceFinacement(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sourceFinacementService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSourceFinacement(
            @RequestBody @Valid final SourceFinacementDTO sourceFinacementDTO) {
        final Long createdId = sourceFinacementService.create(sourceFinacementDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSourceFinacement(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SourceFinacementDTO sourceFinacementDTO) {
        sourceFinacementService.update(id, sourceFinacementDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSourceFinacement(@PathVariable(name = "id") final Long id) {
        sourceFinacementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
