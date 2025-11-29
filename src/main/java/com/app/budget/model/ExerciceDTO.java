package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExerciceDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @ExerciceCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    @ExerciceLibelleUnique
    private String libelle;

    @NotNull
    @ExerciceDateDebutUnique
    private LocalDate dateDebut;

    @ExerciceDateFinUnique
    private LocalDate dateFin;

    
    private Boolean preparation;
    private Boolean execution;
    private Boolean cloture;

    public ExerciceDTO(Long id) {
        this.id = id;
    }

    public ExerciceDTO() {
    }
}
