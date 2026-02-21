package com.app.budget.rest;

import com.app.budget.model.LiquidationDTO;
import com.app.budget.model.ObservationDto;
import com.app.budget.service.LiquidationService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
    public ResponseEntity<List<LiquidationDTO>> getAllLiquidations(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam Long categore,
        @RequestParam Long activite,
        @RequestParam Boolean validation,
         @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin,
        @RequestParam Integer page, @RequestParam Integer size
    ) {
        return ResponseEntity.ok(liquidationService.findAllEntenteEtRetourner(projet,exercice,categore,activite,debut,fin,page,size));
    }

    @GetMapping("/traitement")
    public ResponseEntity<List<LiquidationDTO>> getAllEntenteEreceptionne(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categore,
        @RequestParam(required = false) Long activite,
        @RequestParam(required = false) Boolean validation,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(liquidationService.findAllEntenteEtReceptioner(projet,exercice,categore,activite,debut,fin,page,size));
    }

    @GetMapping("/valider")
    public ResponseEntity<List<LiquidationDTO>> getAllValider(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(liquidationService.getAllValider(projet,exercice,categorie,debut,fin,page,size));
    }

    
    @GetMapping("/rejeter")
    public ResponseEntity<List<LiquidationDTO>> getAllRejeter(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(liquidationService.getAllRejeter(projet,exercice,categorie,debut,fin,page,size));
    }

        @GetMapping("/retourner")
    public ResponseEntity<List<LiquidationDTO>> getAllRetourner(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(liquidationService.getAllRetourner(projet,exercice,categorie,debut,fin,page,size));
    }

    
        @GetMapping("/receptionner")
    public ResponseEntity<List<LiquidationDTO>> getAllReceptionner(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(liquidationService.getAllReceptionner(projet,exercice,categorie,debut,fin,page,size));
    }

       @GetMapping("/en_attante")
    public ResponseEntity<List<LiquidationDTO>> getAllAttenter(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(liquidationService.getAllAttenter(projet,exercice,categorie,debut,fin,page,size));
    }

    @GetMapping("/etat")
    public ResponseEntity<List<LiquidationDTO>> getAllValiderEtRejet(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam Long categore,
        @RequestParam Long activite,
        @RequestParam Boolean validation,
         @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin,
        @RequestParam Integer page, @RequestParam Integer size
    ) {
        return ResponseEntity.ok(liquidationService.findAllRejeterEtValider(projet,exercice,categore,activite,debut,fin,page,size));
    }

    @GetMapping("/montant")
    public ResponseEntity<BigDecimal> getMontantLiquider(@RequestParam Long exercice,@RequestParam Long engagement) {
        return ResponseEntity.ok(liquidationService.getMontantLiquider(exercice,engagement));
    }
      
    @GetMapping("/{id}")
    public ResponseEntity<LiquidationDTO> getLiquidation(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(liquidationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLiquidation(
            @RequestBody @Valid final LiquidationDTO LiquidationDTO) {
        final boolean createdId = liquidationService.create(LiquidationDTO); 
         if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }

    }

    
      @PutMapping("/receptioner/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> receptioner(@PathVariable("id") Long id) {
         if (liquidationService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (liquidationService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = liquidationService.reception(id);
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

       @PutMapping("/valider/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> valider(@PathVariable("id") Long id) {
        if (liquidationService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (liquidationService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = liquidationService.validation(id);
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

      @PutMapping("/rejeter/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> rejeter(@PathVariable("id") Long id,@RequestBody ObservationDto message) {
         if (liquidationService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (liquidationService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
             if (liquidationService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = liquidationService.rejeter(id,message.getObservation());
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

      @PutMapping("/retourner/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> retourner(@PathVariable("id") Long id,@RequestBody ObservationDto message) {
         if (!liquidationService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (liquidationService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = liquidationService.retourne(id,message.getObservation());
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLiquidation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LiquidationDTO LiquidationDTO) { 
        if (liquidationService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
       Boolean createdId= liquidationService.update(id, LiquidationDTO);
         if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLiquidation(@PathVariable(name = "id") final Long id) {
              if (liquidationService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (liquidationService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        liquidationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
