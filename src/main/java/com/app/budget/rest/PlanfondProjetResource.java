package com.app.budget.rest;

import com.app.budget.model.PlanfondProjetDTO;
import com.app.budget.service.PlanfondProjetService;
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
@RequestMapping(value = "/api/planfondProjets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanfondProjetResource {

    private final PlanfondProjetService planfondProjetService;

    public PlanfondProjetResource(final PlanfondProjetService planfondProjetService) {
        this.planfondProjetService = planfondProjetService;
    }

    @GetMapping
    public ResponseEntity<List<PlanfondProjetDTO>> getAllPlanfondProjets() {
        return ResponseEntity.ok(planfondProjetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanfondProjetDTO> getPlanfondProjet(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(planfondProjetService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanfondProjet(
            @RequestBody @Valid final PlanfondProjetDTO planfondProjetDTO) {
        final Long createdId = planfondProjetService.create(planfondProjetDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePlanfondProjet(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlanfondProjetDTO planfondProjetDTO) {
        planfondProjetService.update(id, planfondProjetDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanfondProjet(@PathVariable(name = "id") final Long id) {
        planfondProjetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
