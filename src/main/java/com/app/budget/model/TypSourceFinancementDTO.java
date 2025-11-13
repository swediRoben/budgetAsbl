package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TypSourceFinancementDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @TypSourceFinancementLibelleUnique
    private String libelle;

}
