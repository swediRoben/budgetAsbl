package com.app.budget.rest;

import com.app.budget.model.EngagementDTO;
import com.app.budget.model.ObservationDto;
import com.app.budget.model.RapportGlobalExecution;
import com.app.budget.service.EngagementService;
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
        @RequestParam Long categore,
        @RequestParam Long activite,
        @RequestParam Boolean validation,
         @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin,
        @RequestParam Integer page, @RequestParam Integer size
    ) {
        return ResponseEntity.ok(engagementService.findAllEntenteEtRetourner(projet,exercice,categore,activite,debut,fin,page,size));
    }

    @GetMapping("/traitement")
    public ResponseEntity<List<EngagementDTO>> getAllEntenteEreceptionne(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categore,
        @RequestParam(required = false) Long activite,
        @RequestParam(required = false) Boolean validation,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(engagementService.findAllEntenteEtReceptioner(projet,exercice,categore,activite,debut,fin,page,size));
    }

    @GetMapping("/valider")
    public ResponseEntity<List<EngagementDTO>> getAllValider(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(engagementService.getAllValider(projet,exercice,categorie,debut,fin,page,size));
    }


    @GetMapping("/engagementvaliderliquidation")
    public ResponseEntity<List<EngagementDTO>> getAllValiderInLiquidation(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long ligne)
    {
        return ResponseEntity.ok(engagementService.getAllValiderInLiquidation(projet,exercice,ligne));
    }

    
    @GetMapping("/rejeter")
    public ResponseEntity<List<EngagementDTO>> getAllRejeter(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(engagementService.getAllRejeter(projet,exercice,categorie,debut,fin,page,size));
    }

        @GetMapping("/retourner")
    public ResponseEntity<List<EngagementDTO>> getAllRetourner(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(engagementService.getAllRetourner(projet,exercice,categorie,debut,fin,page,size));
    }

    
        @GetMapping("/receptionner")
    public ResponseEntity<List<EngagementDTO>> getAllReceptionner(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(engagementService.getAllReceptionner(projet,exercice,categorie,debut,fin,page,size));
    }

       @GetMapping("/en_attante")
    public ResponseEntity<List<EngagementDTO>> getAllAttenter(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam(required = false) Long categorie,
         @RequestParam(required = false) OffsetDateTime debut,
        @RequestParam(required = false) OffsetDateTime fin,
        @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(engagementService.getAllAttenter(projet,exercice,categorie,debut,fin,page,size));
    }

    @GetMapping("/etat")
    public ResponseEntity<List<EngagementDTO>> getAllValiderEtRejet(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam Long categore,
        @RequestParam Long activite,
        @RequestParam Boolean validation,
         @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin,
        @RequestParam Integer page, @RequestParam Integer size
    ) {
        return ResponseEntity.ok(engagementService.findAllRejeterEtValider(projet,exercice,categore,activite,debut,fin,page,size));
    }

    @GetMapping("/montant")
    public ResponseEntity<BigDecimal> getSommeEngager(@RequestParam Long exercice,@RequestParam Long ligne) {
        return ResponseEntity.ok(engagementService.getMontantEngage(exercice,ligne));
    }
      
    @GetMapping("/{id}")
    public ResponseEntity<EngagementDTO> getEngagement(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(engagementService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEngagement(
            @RequestBody @Valid final EngagementDTO engagementDTO) {
        final boolean createdId = engagementService.create(engagementDTO); 
         if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }

    }

    
      @PutMapping("/receptioner/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> receptioner(@PathVariable("id") Long id) {
         if (engagementService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = engagementService.reception(id);
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

       @PutMapping("/valider/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> valider(@PathVariable("id") Long id) {
        if (engagementService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = engagementService.validation(id);
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

      @PutMapping("/rejeter/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> rejeter(@PathVariable("id") Long id,@RequestBody ObservationDto message) {
         if (engagementService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
             if (engagementService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = engagementService.rejeter(id,message.getObservation());
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

      @PutMapping("/retourner/{id}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> retourner(@PathVariable("id") Long id,@RequestBody ObservationDto message) {
         if (!engagementService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = engagementService.retourne(id,message.getObservation());
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEngagement(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EngagementDTO engagementDTO) { 
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
       Boolean createdId= engagementService.update(id, engagementDTO);
         if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEngagement(@PathVariable(name = "id") final Long id) {
              if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
           if (engagementService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        engagementService.delete(id);
        return ResponseEntity.noContent().build();
    }

        @GetMapping("/rapportGeneral")
    public ResponseEntity<List<RapportGlobalExecution>> getAllRapportGeneral(
       @RequestParam Long projet,
        @RequestParam Long exercice,
        @RequestParam Long categore
    ) {
        return ResponseEntity.ok(engagementService.rapportGlobalExecution(projet,exercice,categore));
    }
}
