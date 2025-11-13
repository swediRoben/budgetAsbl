package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategorieDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @CategorieCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    @CategorieLibelleUnique
    private String libelle;

    private Long projetId;

}
