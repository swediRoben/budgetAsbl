package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SourceFinacementDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @SourceFinacementCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    @SourceFinacementLibelleUnique
    private String libelle;

    private Long idTypeSourcefinancement;

}
