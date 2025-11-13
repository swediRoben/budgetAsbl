package com.app.budget.rest;

import com.app.budget.model.PlanComptableDTO;
import com.app.budget.service.PlanComptableService;
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
@RequestMapping(value = "/api/planComptables", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanComptableResource {

    private final PlanComptableService planComptableService;

    public PlanComptableResource(final PlanComptableService planComptableService) {
        this.planComptableService = planComptableService;
    }

    @GetMapping
    public ResponseEntity<List<PlanComptableDTO>> getAllPlanComptables() {
        return ResponseEntity.ok(planComptableService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanComptableDTO> getPlanComptable(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(planComptableService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanComptable(
            @RequestBody @Valid final PlanComptableDTO planComptableDTO) {
        final Long createdId = planComptableService.create(planComptableDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePlanComptable(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlanComptableDTO planComptableDTO) {
        planComptableService.update(id, planComptableDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanComptable(@PathVariable(name = "id") final Long id) {
        planComptableService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
