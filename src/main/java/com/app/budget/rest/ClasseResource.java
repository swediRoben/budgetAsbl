package com.app.budget.rest;

import com.app.budget.model.ClasseDTO;
import com.app.budget.service.ClasseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/classes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClasseResource {

    private final ClasseService classeService;

    public ClasseResource(final ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping
    public ResponseEntity<List<ClasseDTO>> getAllClasses() {
        return ResponseEntity.ok(classeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> getClasse(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(classeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createClasse(@RequestBody @Valid final ClasseDTO classeDTO) {
        final Long createdId = classeService.create(classeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateClasse(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ClasseDTO classeDTO) {
        classeService.update(id, classeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClasse(@PathVariable(name = "id") final Long id) {
        classeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
