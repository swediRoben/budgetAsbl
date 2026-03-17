package com.app.budget.model;
 
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanfondProjetDTO {

    private Long id;

    private Long idProjet;

    private Long idExercice;

    private Long idSource;
    private ProjetDTO projet;
    private ExerciceDTO exercice;
    private SourceFinacementDTO  sourceFinacement; 
    private BigDecimal montant;

}
