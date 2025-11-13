package com.app.budget.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanActiviteDTO {

    private Long id;

    private Long idExercice;

    private Long idProjet;

    private Long idCategorie;

    private Long idActivite;

    private Long idSource;

    private Long idPlanComptable;

    private Long idBeneficiaire;

    @Size(max = 255)
    private String ligneBudgetaire;

    @Size(max = 255)
    private String uniteMesure;

    private Long quantite;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "96.08")
    private BigDecimal prixUnitaire;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "95.08")
    private BigDecimal montant;

}
