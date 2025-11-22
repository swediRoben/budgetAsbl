package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class TypSourceFinancementDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @TypSourceFinancementLibelleUnique
    private String libelle;

    public TypSourceFinancementDTO(Long id) {
        this.id = id;
    }
    public TypSourceFinancementDTO() {
    }
}
