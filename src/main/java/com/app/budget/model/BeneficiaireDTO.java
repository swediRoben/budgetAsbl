package com.app.budget.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BeneficiaireDTO {

    private Long id;

    @Size(max = 255)
    private String libelle;

}
