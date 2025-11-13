package com.app.budget.service;

import com.app.budget.domain.Engagement;
import com.app.budget.events.BeforeDeleteEngagement;
import com.app.budget.model.EngagementDTO;
import com.app.budget.repos.EngagementRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
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

    public List<EngagementDTO> findAll() {
        final List<Engagement> engagements = engagementRepository.findAll(Sort.by("id"));
        return engagements.stream()
                .map(engagement -> mapToDTO(engagement, new EngagementDTO()))
                .toList();
    }

    public EngagementDTO get(final Long id) {
        return engagementRepository.findById(id)
                .map(engagement -> mapToDTO(engagement, new EngagementDTO()))
                .orElseThrow(NotFoundException::new);
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
