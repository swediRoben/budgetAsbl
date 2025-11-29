package com.app.budget.service;

import com.app.budget.domain.PlanActivite;
import com.app.budget.model.PlanActiviteDTO;
import com.app.budget.repos.PlanActiviteRepository;
import com.app.budget.util.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanActiviteService {

    private final PlanActiviteRepository planActiviteRepository;

    public PlanActiviteService(final PlanActiviteRepository planActiviteRepository) {
        this.planActiviteRepository = planActiviteRepository;
    }

    public List<PlanActiviteDTO> findAll() {
        final List<PlanActivite> planActivites = planActiviteRepository.findAll(Sort.by("id"));
        return planActivites.stream()
                .map(planActivite -> mapToDTO(planActivite, new PlanActiviteDTO()))
                .toList();
    }

    public PlanActiviteDTO get(final Long id) {
        return planActiviteRepository.findById(id)
                .map(planActivite -> mapToDTO(planActivite, new PlanActiviteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanActiviteDTO planActiviteDTO) {
        final PlanActivite planActivite = new PlanActivite();
        mapToEntity(planActiviteDTO, planActivite);
        return planActiviteRepository.save(planActivite).getId();
    }

    public void update(final Long id, final PlanActiviteDTO planActiviteDTO) {
        final PlanActivite planActivite = planActiviteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planActiviteDTO, planActivite);
        planActiviteRepository.save(planActivite);
    }

    public void delete(final Long id) {
        final PlanActivite planActivite = planActiviteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        planActiviteRepository.delete(planActivite);
    }

    private PlanActiviteDTO mapToDTO(final PlanActivite planActivite,
            final PlanActiviteDTO planActiviteDTO) {
        planActiviteDTO.setId(planActivite.getId());
        planActiviteDTO.setIdExercice(planActivite.getIdExercice());
        planActiviteDTO.setIdProjet(planActivite.getIdProjet());
        planActiviteDTO.setIdCategorie(planActivite.getIdCategorie());
        planActiviteDTO.setIdActivite(planActivite.getIdActivite());
        planActiviteDTO.setIdSource(planActivite.getIdSource());
        planActiviteDTO.setIdClasse(planActivite.getIdClasse());
        planActiviteDTO.setIdPlanComptable(planActivite.getIdPlanComptable());
        planActiviteDTO.setIdBeneficiaire(planActivite.getIdBeneficiaire());
        planActiviteDTO.setLigneBudgetaire(planActivite.getLigneBudgetaire());
        planActiviteDTO.setUniteMesure(planActivite.getUniteMesure());
        planActiviteDTO.setQuantite(planActivite.getQuantite());
        planActiviteDTO.setPrixUnitaire(planActivite.getPrixUnitaire());
        planActiviteDTO.setMontant(planActivite.getMontant());
        planActiviteDTO.setDebut(planActivite.getDebut());
        planActiviteDTO.setFin(planActivite.getFin());
         planActiviteDTO.setProjet(planActivite.getProjet());
            planActiviteDTO.setCategorie(planActivite.getCategorie());
            planActiviteDTO.setActivite(planActivite.getActivite());
            planActiviteDTO.setIdSource(planActivite.getSource().getId());
            planActiviteDTO.setPlanComptable(planActivite.getPlanComptable());
            planActiviteDTO.setClasse(planActivite.getClasse());
            planActiviteDTO.setBeneficiaire(planActivite.getBeneficiaire());
  

        return planActiviteDTO;
    }

    private PlanActivite mapToEntity(final PlanActiviteDTO planActiviteDTO,
            final PlanActivite planActivite) {
        planActivite.setIdExercice(planActiviteDTO.getIdExercice());
        planActivite.setIdProjet(planActiviteDTO.getIdProjet());
        planActivite.setIdCategorie(planActiviteDTO.getIdCategorie());
        planActivite.setIdActivite(planActiviteDTO.getIdActivite());
        planActivite.setIdSource(planActiviteDTO.getIdSource());
        planActivite.setIdPlanComptable(planActiviteDTO.getIdPlanComptable());
        planActivite.setIdBeneficiaire(planActiviteDTO.getIdBeneficiaire());
        planActivite.setLigneBudgetaire(planActiviteDTO.getLigneBudgetaire());
        planActivite.setUniteMesure(planActiviteDTO.getUniteMesure());
        planActivite.setQuantite(planActiviteDTO.getQuantite());
        planActivite.setPrixUnitaire(planActiviteDTO.getPrixUnitaire());
        planActivite.setMontant(planActiviteDTO.getMontant());
        planActivite.setDebut(planActiviteDTO.getDebut());
        planActivite.setFin(planActiviteDTO.getFin());
        return planActivite;
    }

    public List<PlanActiviteDTO> findAll(Long projet, Long exercice, Long categorie, Long classe,Long bailleur) {
        List<PlanActivite> planActivites = null;
        List<PlanActiviteDTO> planActiviteDto = new ArrayList<>();
        if(projet==null){
            planActivites = Collections.emptyList();
        }else{
            planActivites=planActiviteRepository.findAllByAllData(exercice,projet,categorie,classe,bailleur);
        }
       planActivites.forEach(v -> {
            planActiviteDto.add(mapToDTO(v, new PlanActiviteDTO()));
        });
        return planActiviteDto;

    }

}
