package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActiviteDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @ActiviteCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    @ActiviteLibelleUnique
    private String libelle;

    private CategorieDTO categorie;

}
