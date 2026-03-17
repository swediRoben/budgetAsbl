package com.app.budget.service;
 
import com.app.budget.domain.Liquidation; 
import com.app.budget.model.LiquidationDTO;
import com.app.budget.model.NombreExecution;
import com.app.budget.repos.LiquidationRepository;
import com.app.budget.util.NotFoundException;

import jakarta.transaction.Transactional;

import java.math.BigDecimal; 
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;


@Service
public class LiquidationService {

    private final LiquidationRepository liquidationRepository;
    private final ApplicationEventPublisher publisher; 

    public LiquidationService(LiquidationRepository liquidationRepository, ApplicationEventPublisher publisher) {
        this.liquidationRepository = liquidationRepository;
        this.publisher = publisher;
    }

    public List<LiquidationDTO> findAllEntenteEtRetourner(
            Long projet,
            Long exercice,
            Long categorie,
            Long activite, 
            OffsetDateTime debut,
            OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);

        Page<Liquidation> LiquidationsPage = liquidationRepository.findAllEntenteEtRetourner(
                exercice, projet, activite,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
    }

       public List<LiquidationDTO> findAllEntenteEtReceptioner(
            Long projet,
            Long exercice,
            Long categorie,
            Long activite, 
            OffsetDateTime debut,
            OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);

        Page<Liquidation> LiquidationsPage = liquidationRepository.findAllEntenteEtReceptioner(
                exercice, projet, activite,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
    }

    public List<LiquidationDTO> findAllRejeterEtValider(
            Long projet,
            Long exercice,
            Long categorie,
            Long activite, 
            OffsetDateTime debut,
            OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Liquidation> LiquidationsPage = liquidationRepository.findAllEntenteEtReceptioner(
                exercice, projet, activite,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
    }


    public LiquidationDTO get(final Long id) {
        return liquidationRepository.findById(id)
                .map(Liquidation -> mapToDTO(Liquidation, new LiquidationDTO()))
                .orElseThrow(NotFoundException::new);
    }

  public BigDecimal getMontantLiquider(Long exercice,Long engagement) {
        return liquidationRepository.sumMontantNotAnnuler(exercice,engagement);
    }

    @Transactional
    public boolean create(final LiquidationDTO LiquidationDTO) {
        try {  
         final Liquidation Liquidation = new Liquidation();
        mapToEntity(LiquidationDTO, Liquidation); 
        liquidationRepository.save(Liquidation);
        return true;
        } catch (Exception e) {
        return false;
        }
    }

    public boolean update(final Long id, final LiquidationDTO LiquidationDTO) {
      
        try {
          final Liquidation Liquidation = liquidationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(LiquidationDTO, Liquidation);
        liquidationRepository.save(Liquidation);    
       return true;
        } catch (Exception e) {
        return false;
        }
    }

    public Boolean validation(final Long id) {
        final Liquidation Liquidation = liquidationRepository.findById(id).get();
        try {
         Liquidation.setValidation(true);
         Liquidation.setEnAttente(false);
         Liquidation.setRetourner(false);
         Liquidation.setReception(false);
         Liquidation.setRejet(false);
         Liquidation.setDataValidation(OffsetDateTime.now());
        liquidationRepository.save(Liquidation);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

        public Boolean reception(final Long id) {
        final Liquidation Liquidation = liquidationRepository.findById(id).get();
        try {
         Liquidation.setReception(true);
         Liquidation.setValidation(false);
         Liquidation.setEnAttente(false);
         Liquidation.setRetourner(false); 
         Liquidation.setRejet(false);
         Liquidation.setDataReception(OffsetDateTime.now());
        liquidationRepository.save(Liquidation);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

    public boolean checkValidation(Long id){
        Optional<Liquidation> data=liquidationRepository.findByIdAndValidation(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }

    
    public boolean checkRejeter(Long id){
        Optional<Liquidation> data=liquidationRepository.findByIdAndRejet(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }

        
    public boolean checkReceptioner(Long id){
        Optional<Liquidation> data=liquidationRepository.findByIdAndReception(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }
        
    public boolean checkRetourner(Long id){
        Optional<Liquidation> data=liquidationRepository.findByIdAndRetourner(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }

    public Boolean rejeter(final Long id,String message) {
        final Liquidation Liquidation = liquidationRepository.findById(id).get();
        try {
         Liquidation.setRejet(true);
         Liquidation.setValidation(false);
         Liquidation.setEnAttente(false);
         Liquidation.setRetourner(false);
         Liquidation.setReception(false); 
         Liquidation.setDataRejet(OffsetDateTime.now());
         Liquidation.setObservation(message);
        liquidationRepository.save(Liquidation);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

      public Boolean retourne(final Long id,String message) {
        final Liquidation Liquidation = liquidationRepository.findById(id).get();
        try {
         Liquidation.setRejet(false);
         Liquidation.setValidation(false);
         Liquidation.setEnAttente(false);
         Liquidation.setRetourner(true);
         Liquidation.setReception(false); 
         Liquidation.setDataRetourner(OffsetDateTime.now());
         Liquidation.setObservation(message);
        liquidationRepository.save(Liquidation);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

    public void delete(final Long id) {
        final Liquidation Liquidation = liquidationRepository.findById(id)
                .orElseThrow(NotFoundException::new); 
        liquidationRepository.delete(Liquidation);
    }

    private LiquidationDTO mapToDTO(final Liquidation Liquidation, final LiquidationDTO LiquidationDTO) {
        LiquidationDTO.setId(Liquidation.getId());
        LiquidationDTO.setBonEngagment(Liquidation.getBonEngagment());
        LiquidationDTO.setIdEngagement(Liquidation.getIdEngagement());
        LiquidationDTO.setPiece(Liquidation.getPiece());
        LiquidationDTO.setIdExercice(Liquidation.getIdExercice()); 
        LiquidationDTO.setDataEnAttente(Liquidation.getDataEnAttente());
        LiquidationDTO.setDataReception(Liquidation.getDataReception());
        LiquidationDTO.setDataValidation(Liquidation.getDataValidation()); 
        LiquidationDTO.setDataRetourner(Liquidation.getDataRetourner()); 
        LiquidationDTO.setDataRejet(Liquidation.getDataRejet());
        LiquidationDTO.setEnAttente(Liquidation.getEnAttente());
        LiquidationDTO.setValidation(Liquidation.getValidation());
        LiquidationDTO.setReception(Liquidation.getReception());
        LiquidationDTO.setRetourner(Liquidation.getRetourner());
        LiquidationDTO.setRejet(Liquidation.getRejet());
        LiquidationDTO.setMontant(Liquidation.getMontant());
        LiquidationDTO.setIdDevise(Liquidation.getIdDevise()); 
        LiquidationDTO.setTauxDevise(Liquidation.getTauxDevise());
        LiquidationDTO.setIdProjet(Liquidation.getIdProjet());
        LiquidationDTO.setIdCategorie(Liquidation.getIdCategorie());
        LiquidationDTO.setIdPlanFondActivite(Liquidation.getIdPlanFondActivite());
        LiquidationDTO.setPlanActivite(Liquidation.getPlanActivite());
        LiquidationDTO.setIdResponsable(Liquidation.getIdResponsable());
        LiquidationDTO.setObjet(Liquidation.getObjet());
        LiquidationDTO.setDevise(Liquidation.getDevise());
        LiquidationDTO.setObservation(Liquidation.getObservation());  
        LiquidationDTO.setResponsable(Liquidation.getResponsable()); 
        return LiquidationDTO;
    }

    private Liquidation mapToEntity(final LiquidationDTO LiquidationDTO, final Liquidation Liquidation) {
               Liquidation.setId(LiquidationDTO.getId());
        Liquidation.setBonEngagment(LiquidationDTO.getBonEngagment());
        Liquidation.setPiece(LiquidationDTO.getPiece());
        Liquidation.setIdExercice(LiquidationDTO.getIdExercice()); 
        Liquidation.setIdEngagement(LiquidationDTO.getIdEngagement());
        Liquidation.setIdCategorie(LiquidationDTO.getIdCategorie());
        Liquidation.setDataEnAttente(OffsetDateTime.now());
        Liquidation.setEnAttente(true);
         Liquidation.setValidation(false); 
         Liquidation.setRetourner(false);
         Liquidation.setReception(false);
         Liquidation.setRejet(false);
        Liquidation.setMontant(LiquidationDTO.getMontant());
        Liquidation.setIdDevise(LiquidationDTO.getIdDevise()); 
        Liquidation.setTauxDevise(LiquidationDTO.getTauxDevise()); 
        Liquidation.setIdProjet(LiquidationDTO.getIdProjet());
        Liquidation.setIdResponsable(LiquidationDTO.getIdResponsable());
        Liquidation.setObjet(LiquidationDTO.getObjet());
        Liquidation.setObservation(LiquidationDTO.getObservation());   
        Liquidation.setIdCategorie(LiquidationDTO.getIdCategorie());
        Liquidation.setIdPlanFondActivite(LiquidationDTO.getIdPlanFondActivite());
        return Liquidation;
    }

  
 
    public List<LiquidationDTO>  getAllAttenter(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Liquidation> LiquidationsPage = liquidationRepository.getAllAttenter(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
  }



    public  List<LiquidationDTO> getAllReceptionner(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
          Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Liquidation> LiquidationsPage = liquidationRepository.getAllReceptionner(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
    }
 

    public  List<LiquidationDTO> getAllRetourner(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
       
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Liquidation> LiquidationsPage = liquidationRepository.getAllRetourner(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
  }

    public  List<LiquidationDTO> getAllRejeter(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
        
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Liquidation> LiquidationsPage = liquidationRepository.getAllRejeter(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
  }

    public List<LiquidationDTO> getAllValider(Long projet, Long exercice,Long categorie, OffsetDateTime debut,OffsetDateTime fin, Integer page,
            Integer size) {
       
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Liquidation> LiquidationsPage = liquidationRepository.getAllValider(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<LiquidationDTO> dtos = LiquidationsPage.stream()
                .map(e -> mapToDTO(e, new LiquidationDTO()))
                .toList();

        return dtos; 
  }

    public NombreExecution getNombreEngagement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombreEngagement'");
    }

}
