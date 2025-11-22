package com.app.budget.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanFondDTO {

    private Long id;

    private Long idProjet;

    private Long idCategorie;

    private Long idExercice;
    private Long idSource;

    private ProjetDTO projet;
    private ExerciceDTO exercice;
    private CategorieDTO categorie;
    private SourceFinacementDTO sourceFinacement;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "95.08")
    private BigDecimal montant;

}
