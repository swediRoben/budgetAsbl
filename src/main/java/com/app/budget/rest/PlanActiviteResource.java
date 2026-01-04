package com.app.budget.rest;

import com.app.budget.model.PlanActiviteDTO;
import com.app.budget.service.PlanActiviteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/planActivites", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanActiviteResource {

    private final PlanActiviteService planActiviteService;

    public PlanActiviteResource(final PlanActiviteService planActiviteService) {
        this.planActiviteService = planActiviteService;
    }

    @GetMapping
    public ResponseEntity<List<PlanActiviteDTO>> getAllPlanActivites(@RequestParam Long projet, @RequestParam Long exercice, @RequestParam(required = false) Long categorie,@RequestParam(required = false) Long classe,@RequestParam(required = false) Long bailleur) {
        return ResponseEntity.ok(planActiviteService.findAll(projet,exercice,categorie,classe,bailleur));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanActiviteDTO> getPlanActivite(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(planActiviteService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanActivite(
            @RequestBody @Valid final PlanActiviteDTO planActiviteDTO) {
        final Long createdId = planActiviteService.create(planActiviteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePlanActivite(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlanActiviteDTO planActiviteDTO) {
        planActiviteService.update(id, planActiviteDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanActivite(@PathVariable(name = "id") final Long id) {
        planActiviteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
