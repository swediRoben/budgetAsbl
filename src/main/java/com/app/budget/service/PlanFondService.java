package com.app.budget.service;

import com.app.budget.domain.Categorie;
import com.app.budget.domain.Exercice;
import com.app.budget.domain.PlanFond;
import com.app.budget.domain.Projet;
import com.app.budget.model.*;
import com.app.budget.repos.PlanFondRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanFondService {

    private final PlanFondRepository planFondRepository;
    private final CategorieService categorieService;
    private final ProjetService projetService;
    private final ExerciceService exerciceService;


    public PlanFondService(final PlanFondRepository planFondRepository,
                           CategorieService categorieService, ProjetService projetService, ExerciceService exerciceService) {
        this.planFondRepository = planFondRepository;
        this.categorieService = categorieService;
        this.projetService = projetService;
        this.exerciceService = exerciceService;
    }

    public List<PlanFondDTO> findAll(Long projet,Long exercice) {
        List<PlanFond> planFonds = null;
if(projet==null&&exercice==null){
    planFonds = planFondRepository.findAll(Sort.by("id"));
}else {planFonds=planFondRepository.findByProjetId_IdOrExerciceId_Id(projet,exercice);}
        //final List<PlanFond> planFonds = planFondRepository.findAll(Sort.by("id"));
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
        planFondDTO.setSourceFinacement(planFond.getSourceFinacementId()!=null?SourceMapper.getInstance().mapToDTO(planFond.getSourceFinacementId()) :null );
        planFondDTO.setProjet(planFond.getProjetId()!=null?ProjetMapper.getInstance().mapToDTO(planFond.getProjetId()):null);
        planFondDTO.setCategorie(planFond.getCategorieId()!=null?categorieService.mapToDTO(planFond.getCategorieId(),new CategorieDTO()):null);
        planFondDTO.setExercice(planFond.getExerciceId()!=null?ExerciceMapper.getInstance().mapToDTO(planFond.getExerciceId()):null);
        planFondDTO.setMontant(planFond.getMontant());
        return planFondDTO;
    }

    private PlanFond mapToEntity(final PlanFondDTO planFondDTO, final PlanFond planFond) {

        planFond.setProjetId(planFondDTO.getIdProjet()!=null? ProjetMapper.getInstance().mapToEntity(new ProjetDTO(planFondDTO.getIdProjet())) :null);
        planFond.setCategorieId(planFondDTO.getIdCategorie()!=null?categorieService.
                mapToEntity(new CategorieDTO(planFondDTO.getIdProjet()),new Categorie()):null);
        planFond.setExerciceId(planFondDTO.getIdExercice()!=null?ExerciceMapper.getInstance().mapToEntity(new ExerciceDTO(planFondDTO.getIdExercice())):null);
        planFond.setMontant(planFondDTO.getMontant());
        planFond.setSourceFinacementId(planFondDTO.getIdSource()!=null?SourceMapper.getInstance().mapToEntity(new SourceFinacementDTO(planFondDTO.getIdSource())) :null );
        return planFond;
    }

}
