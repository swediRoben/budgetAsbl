package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeviseDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @DeviseCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    @DeviseLibelleUnique
    private String libelle;

    @NotNull
    @Size(max = 255)
    @DeviseSymboleUnique
    private String symbole;

    private Boolean actif;

}
