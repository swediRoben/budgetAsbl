package com.app.budget.service;

import com.app.budget.domain.PlanFond;
import com.app.budget.model.PlanFondDTO;
import com.app.budget.repos.PlanFondRepository;
import com.app.budget.util.NotFoundException;

import jakarta.persistence.Column;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanFondService {

    private final PlanFondRepository planFondRepository;

    public PlanFondService(final PlanFondRepository planFondRepository) {
        this.planFondRepository = planFondRepository;
    }

    public List<PlanFondDTO> findAll() {
        final List<PlanFond> planFonds = planFondRepository.findAll(Sort.by("id"));
        return planFonds.stream()
                .map(planFond -> mapToDTO(planFond, new PlanFondDTO()))
                .toList();
    }

    public PlanFondDTO get(final Long id) {
        return planFondRepository.findById(id)
                .map(planFond -> mapToDTO(planFond, new PlanFondDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanFondDTO planFondDTO) {
        final PlanFond planFond = new PlanFond();
        mapToEntity(planFondDTO, planFond);
        return planFondRepository.save(planFond).getId();
    }

    public void update(final Long id, final PlanFondDTO planFondDTO) {
        final PlanFond planFond = planFondRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planFondDTO, planFond);
        planFondRepository.save(planFond);
    }

    public void delete(final Long id) {
        final PlanFond planFond = planFondRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        planFondRepository.delete(planFond);
    }

    private PlanFondDTO mapToDTO(final PlanFond planFond, final PlanFondDTO planFondDTO) {
        planFondDTO.setId(planFond.getId());
        planFondDTO.setIdProjet(planFond.getIdProjet());
        planFondDTO.setCategorie(planFond.getCategorie());
        planFondDTO.setIdExercice(planFond.getIdExercice());
        planFondDTO.setMontant(planFond.getMontant());
        return planFondDTO;
    }

    private PlanFond mapToEntity(final PlanFondDTO planFondDTO, final PlanFond planFond) {
        planFond.setIdProjet(planFondDTO.getIdProjet());
        planFond.setCategorie(planFondDTO.getCategorie());
        planFond.setIdExercice(planFondDTO.getIdExercice());
        planFond.setMontant(planFondDTO.getMontant());
        return planFond;
    }
 
    public List<PlanFondDTO> findAll(Long exercice, Long projet) {
        final List<PlanFond> planFonds = planFondRepository.findByIdExerciceAndIdProjet(exercice,projet);
        return planFonds.stream()
                .map(planFond -> mapToDTO(planFond, new PlanFondDTO()))
                .toList();
    }

}
