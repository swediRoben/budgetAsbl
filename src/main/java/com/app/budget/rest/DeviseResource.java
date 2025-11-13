package com.app.budget.rest;

import com.app.budget.model.DeviseDTO;
import com.app.budget.service.DeviseService;
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
@RequestMapping(value = "/api/devises", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviseResource {

    private final DeviseService deviseService;

    public DeviseResource(final DeviseService deviseService) {
        this.deviseService = deviseService;
    }

    @GetMapping
    public ResponseEntity<List<DeviseDTO>> getAllDevises() {
        return ResponseEntity.ok(deviseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviseDTO> getDevise(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(deviseService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDevise(@RequestBody @Valid final DeviseDTO deviseDTO) {
        final Long createdId = deviseService.create(deviseDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDevise(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DeviseDTO deviseDTO) {
        deviseService.update(id, deviseDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDevise(@PathVariable(name = "id") final Long id) {
        deviseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
