package com.app.budget.service;

import com.app.budget.domain.Engagement;
import com.app.budget.domain.Liquidation;
import com.app.budget.events.BeforeDeleteEngagement;
import com.app.budget.model.LiquidationDTO;
import com.app.budget.repos.EngagementRepository;
import com.app.budget.repos.LiquidationRepository;
import com.app.budget.util.NotFoundException;
import com.app.budget.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LiquidationService {

    private final LiquidationRepository liquidationRepository;
    private final EngagementRepository engagementRepository;

    public LiquidationService(final LiquidationRepository liquidationRepository,
            final EngagementRepository engagementRepository) {
        this.liquidationRepository = liquidationRepository;
        this.engagementRepository = engagementRepository;
    }

    public List<LiquidationDTO> findAll() {
        final List<Liquidation> liquidations = liquidationRepository.findAll(Sort.by("id"));
        return liquidations.stream()
                .map(liquidation -> mapToDTO(liquidation, new LiquidationDTO()))
                .toList();
    }

    public LiquidationDTO get(final Long id) {
        return liquidationRepository.findById(id)
                .map(liquidation -> mapToDTO(liquidation, new LiquidationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LiquidationDTO liquidationDTO) {
        final Liquidation liquidation = new Liquidation();
        mapToEntity(liquidationDTO, liquidation);
        return liquidationRepository.save(liquidation).getId();
    }

    public void update(final Long id, final LiquidationDTO liquidationDTO) {
        final Liquidation liquidation = liquidationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(liquidationDTO, liquidation);
        liquidationRepository.save(liquidation);
    }

    public void delete(final Long id) {
        final Liquidation liquidation = liquidationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        liquidationRepository.delete(liquidation);
    }

    private LiquidationDTO mapToDTO(final Liquidation liquidation,
            final LiquidationDTO liquidationDTO) {
        liquidationDTO.setId(liquidation.getId());
        liquidationDTO.setIdEngagement(liquidation.getIdEngagement());
        liquidationDTO.setBonEngagment(liquidation.getBonEngagment());
        liquidationDTO.setIdExercice(liquidation.getIdExercice());
        liquidationDTO.setLigneBudgetaire(liquidation.getLigneBudgetaire());
        liquidationDTO.setIdPlanFondActivite(liquidation.getIdPlanFondActivite());
        liquidationDTO.setMontantEngage(liquidation.getMontantEngage());
        liquidationDTO.setMontantLiquide(liquidation.getMontantLiquide());
        liquidationDTO.setStatus(liquidation.getStatus());
        liquidationDTO.setEngagementId(liquidation.getEngagementId() == null ? null : liquidation.getEngagementId().getId());
        return liquidationDTO;
    }

    private Liquidation mapToEntity(final LiquidationDTO liquidationDTO,
            final Liquidation liquidation) {
        liquidation.setIdEngagement(liquidationDTO.getIdEngagement());
        liquidation.setBonEngagment(liquidationDTO.getBonEngagment());
        liquidation.setIdExercice(liquidationDTO.getIdExercice());
        liquidation.setLigneBudgetaire(liquidationDTO.getLigneBudgetaire());
        liquidation.setIdPlanFondActivite(liquidationDTO.getIdPlanFondActivite());
        liquidation.setMontantEngage(liquidationDTO.getMontantEngage());
        liquidation.setMontantLiquide(liquidationDTO.getMontantLiquide());
        liquidation.setStatus(liquidationDTO.getStatus());
        final Engagement engagementId = liquidationDTO.getEngagementId() == null ? null : engagementRepository.findById(liquidationDTO.getEngagementId())
                .orElseThrow(() -> new NotFoundException("engagementId not found"));
        liquidation.setEngagementId(engagementId);
        return liquidation;
    }

    @EventListener(BeforeDeleteEngagement.class)
    public void on(final BeforeDeleteEngagement event) {
        final ReferencedException referencedException = new ReferencedException();
        final Liquidation engagementIdLiquidation = liquidationRepository.findFirstByEngagementIdId(event.getId());
        if (engagementIdLiquidation != null) {
            referencedException.setKey("engagement.liquidation.engagementId.referenced");
            referencedException.addParam(engagementIdLiquidation.getId());
            throw referencedException;
        }
    }

}
