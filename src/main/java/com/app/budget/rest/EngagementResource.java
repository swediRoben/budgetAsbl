package com.app.budget.rest;

import com.app.budget.model.EngagementDTO;
import com.app.budget.service.EngagementService;
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
@RequestMapping(value = "/api/engagements", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class EngagementResource {

    private final EngagementService engagementService;

    public EngagementResource(final EngagementService engagementService) {
        this.engagementService = engagementService;
    }

    @GetMapping
    public ResponseEntity<List<EngagementDTO>> getAllEngagements() {
        return ResponseEntity.ok(engagementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngagementDTO> getEngagement(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(engagementService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEngagement(
            @RequestBody @Valid final EngagementDTO engagementDTO) {
        final Long createdId = engagementService.create(engagementDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEngagement(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EngagementDTO engagementDTO) {
        engagementService.update(id, engagementDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEngagement(@PathVariable(name = "id") final Long id) {
        engagementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
