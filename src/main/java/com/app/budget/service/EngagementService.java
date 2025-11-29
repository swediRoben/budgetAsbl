package com.app.budget.service;

import com.app.budget.domain.Engagement;
import com.app.budget.events.BeforeDeleteEngagement;
import com.app.budget.model.EngagementDTO;
import com.app.budget.repos.EngagementRepository;
import com.app.budget.util.NotFoundException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;


@Service
public class EngagementService {

    private final EngagementRepository engagementRepository;
    private final ApplicationEventPublisher publisher;

    public EngagementService(final EngagementRepository engagementRepository,
            final ApplicationEventPublisher publisher) {
        this.engagementRepository = engagementRepository;
        this.publisher = publisher;
    }

    public List<EngagementDTO> findAll(
            Long projet,Long exercice,Long categorie,Long activite,
            Boolean validation,OffsetDateTime debut,OffsetDateTime fin,
            Integer page,Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);

        Page<Engagement> engagementsPage = engagementRepository.findAllBy(
                exercice, projet, activite,categorie, validation, debut, fin, pageable
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

  public BigDecimal getMontantEngage(Long exercice,Long projet) {
        return engagementRepository.sumMontantNotAnnuler(exercice,projet);
    }

    public Long create(final EngagementDTO engagementDTO) {
        final Engagement engagement = new Engagement();
        mapToEntity(engagementDTO, engagement);
        return engagementRepository.save(engagement).getId();
    }

    public void update(final Long id, final EngagementDTO engagementDTO) {
        final Engagement engagement = engagementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(engagementDTO, engagement);
        engagementRepository.save(engagement);
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
        engagementDTO.setMontantPrevision(engagement.getMontantPrevision());
        engagementDTO.setMotif(engagement.getMotif());
        engagementDTO.setStatus(engagement.getStatus());
        return engagementDTO;
    }

    private Engagement mapToEntity(final EngagementDTO engagementDTO, final Engagement engagement) {
        engagement.setBonEngagement(engagementDTO.getBonEngagement());
        engagement.setIdExercice(engagementDTO.getIdExercice());
        engagement.setLigneBudgetaire(engagementDTO.getLigneBudgetaire());
        engagement.setIdPlanFondActivite(engagementDTO.getIdPlanFondActivite());
        engagement.setMontantPrevision(engagementDTO.getMontantPrevision());
        engagement.setMotif(engagementDTO.getMotif());
        engagement.setStatus(engagementDTO.getStatus());
        return engagement;
    }

}
