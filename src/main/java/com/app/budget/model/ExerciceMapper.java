package com.app.budget.model;

import com.app.budget.domain.Exercice;

public class ExerciceMapper {
    public static  ExerciceMapper getInstance() {return new ExerciceMapper();}
    public ExerciceDTO mapToDTO(final Exercice exercice) {
        ExerciceDTO exerciceDTO = new ExerciceDTO();
        exerciceDTO.setId(exercice.getId());
        exerciceDTO.setCode(exercice.getCode());
        exerciceDTO.setLibelle(exercice.getLibelle());
        exerciceDTO.setDateDebut(exercice.getDateDebut());
        exerciceDTO.setDateFin(exercice.getDateFin());
        exerciceDTO.setCloture(exercice.getCloture());
        return exerciceDTO;
    }

    public Exercice mapToEntity(final ExerciceDTO exerciceDTO) {
        Exercice  exercice = new Exercice();
        exercice.setId(exerciceDTO.getId());
        exercice.setCode(exerciceDTO.getCode());
        exercice.setLibelle(exerciceDTO.getLibelle());
        exercice.setDateDebut(exerciceDTO.getDateDebut());
        exercice.setDateFin(exerciceDTO.getDateFin());
        exercice.setCloture(exerciceDTO.getCloture());
        return exercice;
    }
}
