package com.app.budget.service;

import com.app.budget.domain.Engagement;
import com.app.budget.events.BeforeDeleteEngagement;
import com.app.budget.model.EngagementDTO;
import com.app.budget.model.PlanActiviteDTO;
import com.app.budget.repos.EngagementRepository;
import com.app.budget.util.NotFoundException;

import jakarta.transaction.Transactional;

import java.math.BigDecimal; 
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;


@Service
public class EngagementService {

    private final EngagementRepository engagementRepository;
    private final ApplicationEventPublisher publisher;
    private final BonEngagementNumeroService numeroService; 

    public EngagementService(EngagementRepository engagementRepository, ApplicationEventPublisher publisher,
            BonEngagementNumeroService numeroService) {
        this.engagementRepository = engagementRepository;
        this.publisher = publisher;
        this.numeroService = numeroService;
    }

    public List<EngagementDTO> findAllEntenteEtRetourner(
            Long projet,
            Long exercice,
            Long categorie,
            Long activite, 
            OffsetDateTime debut,
            OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);

        Page<Engagement> engagementsPage = engagementRepository.findAllEntenteEtRetourner(
                exercice, projet, activite,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
    }

       public List<EngagementDTO> findAllEntenteEtReceptioner(
            Long projet,
            Long exercice,
            Long categorie,
            Long activite, 
            OffsetDateTime debut,
            OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);

        Page<Engagement> engagementsPage = engagementRepository.findAllEntenteEtReceptioner(
                exercice, projet, activite,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
    }

    public List<EngagementDTO> findAllRejeterEtValider(
            Long projet,
            Long exercice,
            Long categorie,
            Long activite, 
            OffsetDateTime debut,
            OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Engagement> engagementsPage = engagementRepository.findAllEntenteEtReceptioner(
                exercice, projet, activite,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
    }


    public EngagementDTO get(final Long id) {
        return engagementRepository.findById(id)
                .map(engagement -> mapToDTO(engagement, new EngagementDTO()))
                .orElseThrow(NotFoundException::new);
    }

  public BigDecimal getMontantEngage(Long exercice,Long ligne) {
        return engagementRepository.sumMontantNotAnnuler(exercice,ligne);
    }

    @Transactional
    public boolean create(final EngagementDTO engagementDTO) {
        try {  
         final Engagement engagement = new Engagement();
        mapToEntity(engagementDTO, engagement);
         String numero = numeroService.generateNumero(OffsetDateTime.now());
        engagement.setBonEngagement(numero);
        engagementRepository.save(engagement);
        return true;
        } catch (Exception e) {
        return false;
        }
    }

    public boolean update(final Long id, final EngagementDTO engagementDTO) {
      
        try {
          final Engagement engagement = engagementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(engagementDTO, engagement);
        engagementRepository.save(engagement);    
       return true;
        } catch (Exception e) {
        return false;
        }
    }

    public Boolean validation(final Long id) {
        final Engagement engagement = engagementRepository.findById(id).get();
        try {
         engagement.setValidation(true);
         engagement.setEnAttente(false);
         engagement.setRetourner(false);
         engagement.setReception(false);
         engagement.setRejet(false);
         engagement.setDataValidation(OffsetDateTime.now());
        engagementRepository.save(engagement);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

        public Boolean reception(final Long id) {
        final Engagement engagement = engagementRepository.findById(id).get();
        try {
         engagement.setReception(true);
         engagement.setValidation(false);
         engagement.setEnAttente(false);
         engagement.setRetourner(false); 
         engagement.setRejet(false);
         engagement.setDataReception(OffsetDateTime.now());
        engagementRepository.save(engagement);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

    public boolean checkValidation(Long id){
        Optional<Engagement> data=engagementRepository.findByIdAndValidation(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }

    
    public boolean checkRejeter(Long id){
        Optional<Engagement> data=engagementRepository.findByIdAndRejet(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }

        
    public boolean checkReceptioner(Long id){
        Optional<Engagement> data=engagementRepository.findByIdAndReception(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }
        
    public boolean checkRetourner(Long id){
        Optional<Engagement> data=engagementRepository.findByIdAndRetourner(id,true);
        if (data.isPresent()) {
            return true;
        }
        return false;
    }

    public Boolean rejeter(final Long id,String message) {
        final Engagement engagement = engagementRepository.findById(id).get();
        try {
         engagement.setRejet(true);
         engagement.setValidation(false);
         engagement.setEnAttente(false);
         engagement.setRetourner(false);
         engagement.setReception(false); 
         engagement.setDataRejet(OffsetDateTime.now());
         engagement.setObservation(message);
        engagementRepository.save(engagement);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

      public Boolean retourne(final Long id,String message) {
        final Engagement engagement = engagementRepository.findById(id).get();
        try {
         engagement.setRejet(false);
         engagement.setValidation(false);
         engagement.setEnAttente(false);
         engagement.setRetourner(true);
         engagement.setReception(false); 
         engagement.setDataRejet(OffsetDateTime.now());
         engagement.setObservation(message);
        engagementRepository.save(engagement);
        return true;
        } catch (Exception e) {
          return false;
        }
    }

    public void delete(final Long id) {
        final Engagement engagement = engagementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteEngagement(id));
        engagementRepository.delete(engagement);
    }

    private EngagementDTO mapToDTO(final Engagement engagement, final EngagementDTO engagementDTO) {
        engagementDTO.setId(engagement.getId());
        engagementDTO.setBonEngagement(engagement.getBonEngagement());
        engagementDTO.setIdExercice(engagement.getIdExercice());
        engagementDTO.setLigneBudgetaire(engagement.getLigneBudgetaire());
        engagementDTO.setIdPlanFondActivite(engagement.getIdPlanFondActivite());
        engagementDTO.setDataEnAttente(engagement.getDataEnAttente());
        engagementDTO.setDataReception(engagement.getDataReception());
        engagementDTO.setDataValidation(engagement.getDataValidation()); 
        engagementDTO.setDataRejet(engagement.getDataRejet());
        engagementDTO.setEnAttente(engagement.getEnAttente());
        engagementDTO.setValidation(engagement.getValidation());
        engagementDTO.setReception(engagement.getReception());
        engagementDTO.setRejet(engagement.getRejet());
        engagementDTO.setMontant(engagement.getMontant());
        engagementDTO.setIdDevise(engagement.getIdDevise());
        engagementDTO.setTauxDevise(engagement.getTauxDevise());
        engagementDTO.setIdProjet(engagement.getIdProjet());
        engagementDTO.setIdResponsable(engagement.getIdResponsable());
        engagementDTO.setObjet(engagement.getObjet());
        engagementDTO.setDevise(engagement.getDevise());
        engagementDTO.setObservation(engagement.getObservation());
        if (engagement.getPlanActivite()!=null) {
           PlanActiviteDTO plan=new PlanActiviteDTO();
           plan.setId(engagement.getPlanActivite().getId());
           plan.setActivite(engagement.getPlanActivite().getActivite());
           engagementDTO.setPlanActivite(plan);
        } 
        engagementDTO.setResponsable(engagement.getResponsable()); 
        return engagementDTO;
    }

    private Engagement mapToEntity(final EngagementDTO engagementDTO, final Engagement engagement) {
               engagement.setId(engagementDTO.getId());
        engagement.setBonEngagement(engagementDTO.getBonEngagement());
        engagement.setIdExercice(engagementDTO.getIdExercice());
        engagement.setLigneBudgetaire(engagementDTO.getLigneBudgetaire());
        engagement.setIdPlanFondActivite(engagementDTO.getIdPlanFondActivite());
        engagement.setDataEnAttente(OffsetDateTime.now());
        engagement.setEnAttente(true);
         engagement.setValidation(false); 
         engagement.setRetourner(false);
         engagement.setReception(false);
         engagement.setRejet(false);
        engagement.setMontant(engagementDTO.getMontant());
        engagement.setIdDevise(engagementDTO.getIdDevise());
        engagement.setTauxDevise(engagementDTO.getTauxDevise());
        engagement.setIdProjet(engagementDTO.getIdProjet());
        engagement.setIdResponsable(engagementDTO.getIdResponsable());
        engagement.setObjet(engagementDTO.getObjet());
        engagement.setObservation(engagementDTO.getObservation());  
        return engagement;
    }

  
 
    public List<EngagementDTO>  getAllAttenter(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Engagement> engagementsPage = engagementRepository.getAllAttenter(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
  }



    public  List<EngagementDTO> getAllReceptionner(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
          Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Engagement> engagementsPage = engagementRepository.getAllReceptionner(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
    }

    public  List<EngagementDTO> getAllRetourner(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
       
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Engagement> engagementsPage = engagementRepository.getAllRetourner(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
  }

    public  List<EngagementDTO> getAllRejeter(Long projet, Long exercice,Long categorie, OffsetDateTime debut, OffsetDateTime fin, Integer page,
            Integer size) {
        
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Engagement> engagementsPage = engagementRepository.getAllRejeter(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
  }

    public List<EngagementDTO> getAllValider(Long projet, Long exercice,Long categorie, OffsetDateTime debut,OffsetDateTime fin, Integer page,
            Integer size) {
       
              Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : size.MAX_VALUE);

        Page<Engagement> engagementsPage = engagementRepository.getAllValider(
                exercice, projet,categorie, debut, fin, pageable
        );

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 
  }

    public List<EngagementDTO> getAllValiderInLiquidation(Long projet, Long exercice, Long ligne) {
        List<Engagement> engagementsPage = engagementRepository.getAllValiderInLiquidation(
                exercice, projet,ligne);

        List<EngagementDTO> dtos = engagementsPage.stream()
                .map(e -> mapToDTO(e, new EngagementDTO()))
                .toList();

        return dtos; 

   }
}