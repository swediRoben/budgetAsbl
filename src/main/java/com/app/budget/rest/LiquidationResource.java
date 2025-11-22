package com.app.budget.rest;

import com.app.budget.model.LiquidationDTO;
import com.app.budget.service.LiquidationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/liquidations", produces = MediaType.APPLICATION_JSON_VALUE)
public class LiquidationResource {

    private final LiquidationService liquidationService;

    public LiquidationResource(final LiquidationService liquidationService) {
        this.liquidationService = liquidationService;
    }

    @GetMapping
    public ResponseEntity<List<LiquidationDTO>> getAllLiquidations() {
        return ResponseEntity.ok(liquidationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiquidationDTO> getLiquidation(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(liquidationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLiquidation(
            @RequestBody @Valid final LiquidationDTO liquidationDTO) {
        final Long createdId = liquidationService.create(liquidationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLiquidation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LiquidationDTO liquidationDTO) {
        liquidationService.update(id, liquidationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLiquidation(@PathVariable(name = "id") final Long id) {
        liquidationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
