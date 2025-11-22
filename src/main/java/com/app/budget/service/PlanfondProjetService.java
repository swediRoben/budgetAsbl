package com.app.budget.service;

import com.app.budget.domain.Exercice;
import com.app.budget.domain.PlanfondProjet;
import com.app.budget.domain.Projet;
import com.app.budget.model.*;
import com.app.budget.repos.PlanfondProjetRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanfondProjetService {

    private final ProjetService projetService;
    private final SourceFinacementService sourceFinacementService;
    private final ExerciceService exerciceService;

    private final PlanfondProjetRepository planfondProjetRepository;

    public PlanfondProjetService(ProjetService projetService,
                                 SourceFinacementService sourceFinacementService,
                                 ExerciceService exerciceService, final PlanfondProjetRepository planfondProjetRepository) {
        this.projetService = projetService;
        this.sourceFinacementService = sourceFinacementService;
        this.exerciceService = exerciceService;
        this.planfondProjetRepository = planfondProjetRepository;
    }

    public List<PlanfondProjetDTO> findAll(Long idExercice) {
        List<PlanfondProjet> planfondProjets = null;
        if (idExercice == null) {
            planfondProjets = planfondProjetRepository.findAll(Sort.by("id"));
        }else {
            planfondProjets=planfondProjetRepository.findByExerciceId_Id(idExercice);
        }
        //final List<PlanfondProjet> planfondProjets = planfondProjetRepository.findAll(Sort.by("id"));
        return planfondProjets.stream()
                .map(planfondProjet ->PlanfondProjetMapper.getInstance().mapToDTO(planfondProjet))
                .toList();
    }

    public PlanfondProjetDTO get(final Long id) {
        return planfondProjetRepository.findById(id)
                .map(planfondProjet -> PlanfondProjetMapper.getInstance().mapToDTO(planfondProjet))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanfondProjetDTO planfondProjetDTO) {
        final PlanfondProjet planfondProjet = new PlanfondProjet();
       // mapToEntity(planfondProjetDTO, planfondProjet);
        return planfondProjetRepository.save(PlanfondProjetMapper.getInstance().mapToEntity(planfondProjetDTO)).getId();
    }

    public void update(final Long id, final PlanfondProjetDTO planfondProjetDTO) {
        final PlanfondProjet planfondProjet = planfondProjetRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        planfondProjetRepository.save(PlanfondProjetMapper.getInstance().mapToEntity(planfondProjetDTO));
    }

    public void delete(final Long id) {
        final PlanfondProjet planfondProjet = planfondProjetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        planfondProjetRepository.delete(planfondProjet);
    }



}
