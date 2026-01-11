package com.app.budget.rest;

import com.app.budget.model.EngagementDTO;
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
        @RequestParam Long categore,
        @RequestParam Long activite,
        @RequestParam Boolean validation,
         @RequestParam OffsetDateTime debut,
        @RequestParam OffsetDateTime fin,
        @RequestParam Integer page, @RequestParam Integer size
    ) {
        return ResponseEntity.ok(engagementService.findAllEntenteEtReceptioner(projet,exercice,categore,activite,debut,fin,page,size));
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
    public ResponseEntity<BigDecimal> getSommeEngager(@RequestParam Long projet,@RequestParam Long exercice) {
        return ResponseEntity.ok(engagementService.getMontantEngage(exercice,projet));
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

    
      @PutMapping("/receptioner")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> receptioner(@PathVariable Long id) {
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

       @PutMapping("/valider")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> valider(@PathVariable Long id) {
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

      @PutMapping("/rejeter")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> rejeter(@PathVariable Long id,@RequestBody String message) {
         if (engagementService.checkRetourner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
             if (engagementService.checkValidation(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = engagementService.rejeter(id,message);
        if (createdId) {
           return  new ResponseEntity<>(null, HttpStatus.CREATED); 
        }else{
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }

      @PutMapping("/retourner")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> retourner(@PathVariable Long id,@RequestBody String message) {
         if (!engagementService.checkReceptioner(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        if (engagementService.checkRejeter(id)) {
          return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);   
        }
        Boolean createdId = engagementService.retourne(id,message);
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

}
