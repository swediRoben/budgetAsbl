package com.app.budget.rest;

import com.app.budget.model.ExerciceDTO;
import com.app.budget.service.ExerciceService;
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
@RequestMapping(value = "/api/exercices", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExerciceResource {

    private final ExerciceService exerciceService;

    public ExerciceResource(final ExerciceService exerciceService) {
        this.exerciceService = exerciceService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciceDTO>> getAllExercices() {
        return ResponseEntity.ok(exerciceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciceDTO> getExercice(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(exerciceService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createExercice(@RequestBody @Valid final ExerciceDTO exerciceDTO) {
        final Long createdId = exerciceService.create(exerciceDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateExercice(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ExerciceDTO exerciceDTO) {
        exerciceService.update(id, exerciceDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteExercice(@PathVariable(name = "id") final Long id) {
        exerciceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
