package com.app.budget.model;

import com.app.budget.domain.Classe;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanComptableDTO {

    private Long id;

    private Long classeId;

    @NotNull
    @Size(max = 255)
    @PlanComptableLibelleUnique
    private String libelle;

    @NotNull
    @Size(max = 255)
    @PlanComptableNumeroUnique
    private String numero;

    @NotNull
    @Size(max = 255)
    private String sens;

    private ClasseDTO classe;

}
