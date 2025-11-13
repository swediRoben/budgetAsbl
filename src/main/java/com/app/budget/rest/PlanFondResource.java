package com.app.budget.rest;

import com.app.budget.model.PlanFondDTO;
import com.app.budget.service.PlanFondService;
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
@RequestMapping(value = "/api/planFonds", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanFondResource {

    private final PlanFondService planFondService;

    public PlanFondResource(final PlanFondService planFondService) {
        this.planFondService = planFondService;
    }

    @GetMapping
    public ResponseEntity<List<PlanFondDTO>> getAllPlanFonds() {
        return ResponseEntity.ok(planFondService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanFondDTO> getPlanFond(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(planFondService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanFond(@RequestBody @Valid final PlanFondDTO planFondDTO) {
        final Long createdId = planFondService.create(planFondDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePlanFond(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlanFondDTO planFondDTO) {
        planFondService.update(id, planFondDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanFond(@PathVariable(name = "id") final Long id) {
        planFondService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
