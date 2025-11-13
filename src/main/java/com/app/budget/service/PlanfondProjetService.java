package com.app.budget.service;

import com.app.budget.domain.PlanfondProjet;
import com.app.budget.model.PlanfondProjetDTO;
import com.app.budget.repos.PlanfondProjetRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanfondProjetService {

    private final PlanfondProjetRepository planfondProjetRepository;

    public PlanfondProjetService(final PlanfondProjetRepository planfondProjetRepository) {
        this.planfondProjetRepository = planfondProjetRepository;
    }

    public List<PlanfondProjetDTO> findAll() {
        final List<PlanfondProjet> planfondProjets = planfondProjetRepository.findAll(Sort.by("id"));
        return planfondProjets.stream()
                .map(planfondProjet -> mapToDTO(planfondProjet, new PlanfondProjetDTO()))
                .toList();
    }

    public PlanfondProjetDTO get(final Long id) {
        return planfondProjetRepository.findById(id)
                .map(planfondProjet -> mapToDTO(planfondProjet, new PlanfondProjetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanfondProjetDTO planfondProjetDTO) {
        final PlanfondProjet planfondProjet = new PlanfondProjet();
        mapToEntity(planfondProjetDTO, planfondProjet);
        return planfondProjetRepository.save(planfondProjet).getId();
    }

    public void update(final Long id, final PlanfondProjetDTO planfondProjetDTO) {
        final PlanfondProjet planfondProjet = planfondProjetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planfondProjetDTO, planfondProjet);
        planfondProjetRepository.save(planfondProjet);
    }

    public void delete(final Long id) {
        final PlanfondProjet planfondProjet = planfondProjetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        planfondProjetRepository.delete(planfondProjet);
    }

    private PlanfondProjetDTO mapToDTO(final PlanfondProjet planfondProjet,
            final PlanfondProjetDTO planfondProjetDTO) {
        planfondProjetDTO.setId(planfondProjet.getId());
        planfondProjetDTO.setIdProjet(planfondProjet.getIdProjet());
        planfondProjetDTO.setIdExercice(planfondProjet.getIdExercice());
        planfondProjetDTO.setIdSource(planfondProjet.getIdSource());
        planfondProjetDTO.setMontant(planfondProjet.getMontant());
        return planfondProjetDTO;
    }

    private PlanfondProjet mapToEntity(final PlanfondProjetDTO planfondProjetDTO,
            final PlanfondProjet planfondProjet) {
        planfondProjet.setIdProjet(planfondProjetDTO.getIdProjet());
        planfondProjet.setIdExercice(planfondProjetDTO.getIdExercice());
        planfondProjet.setIdSource(planfondProjetDTO.getIdSource());
        planfondProjet.setMontant(planfondProjetDTO.getMontant());
        return planfondProjet;
    }

}
