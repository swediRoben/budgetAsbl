package com.app.budget.rest;

import com.app.budget.model.EngagementDTO;
import com.app.budget.service.EngagementService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/engagements", produces = MediaType.APPLICATION_JSON_VALUE)
public class EngagementResource {

    private final EngagementService engagementService;

    public EngagementResource(final EngagementService engagementService) {
        this.engagementService = engagementService;
    }

    @GetMapping
    public ResponseEntity<List<EngagementDTO>> getAllEngagements(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam Long categire,
        @RequestParam Long activite,
        @RequestParam Boolean validation,
         @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin,
        @RequestParam Integer page, @RequestParam Integer size
    ) {
        return ResponseEntity.ok(engagementService.findAll(projet,exercice,categire,activite,validation,debut,fin,page,size));
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
