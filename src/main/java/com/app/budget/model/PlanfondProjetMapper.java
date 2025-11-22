package com.app.budget.model;

import com.app.budget.domain.PlanfondProjet;
import com.app.budget.service.ExerciceService;
import com.app.budget.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanfondProjetMapper {


    public static  PlanfondProjetMapper getInstance() {return new PlanfondProjetMapper();}

    public PlanfondProjetDTO mapToDTO(final PlanfondProjet planfondProjet) {
        PlanfondProjetDTO planfondProjetDTO = new PlanfondProjetDTO();
        planfondProjetDTO.setId(planfondProjet.getId());
        planfondProjetDTO.setProjet(planfondProjet.getProjetId()!=null?
               ProjetMapper.getInstance().mapToDTO(planfondProjet.getProjetId()):null);
        planfondProjetDTO.setExercice(planfondProjet.getExerciceId()!=null?
                ExerciceMapper.getInstance().mapToDTO(planfondProjet.getExerciceId()):null);
        planfondProjetDTO.setSourceFinacement(planfondProjet.getSourceFinacement()!=null?
                SourceMapper.getInstance().mapToDTO(planfondProjet.getSourceFinacement()):null);
        planfondProjetDTO.setIdProjet(planfondProjet.getProjetId()!=null?planfondProjetDTO.getId():null);
        planfondProjetDTO.setIdExercice(planfondProjet.getExerciceId()!=null?planfondProjetDTO.getId():null);
        planfondProjetDTO.setIdSource(planfondProjet.getSourceFinacement()!=null?planfondProjetDTO.getId():null);
        planfondProjetDTO.setMontant(planfondProjet.getMontant());
        return planfondProjetDTO;
    }

    public PlanfondProjet mapToEntity(final PlanfondProjetDTO planfondProjetDTO) {
PlanfondProjet planfondProjet = new PlanfondProjet();
planfondProjet.setId(planfondProjetDTO.getId());
        planfondProjet.setProjetId(planfondProjetDTO.getIdProjet()!=null?
                ProjetMapper.getInstance().mapToEntity(new ProjetDTO(planfondProjetDTO.getIdProjet())):null);
        planfondProjet.setSourceFinacement(
                planfondProjetDTO.getIdSource()!=null?
                        SourceMapper.getInstance().mapToEntity(new SourceFinacementDTO(planfondProjetDTO.getIdSource())):null);
        if(planfondProjetDTO.getIdExercice()!=null){
            planfondProjet.setExerciceId(
                    ExerciceMapper.getInstance().mapToEntity(new ExerciceDTO(planfondProjetDTO.getIdExercice())));
        }
        planfondProjet.setMontant(planfondProjetDTO.getMontant());
        return planfondProjet;
    }
}
