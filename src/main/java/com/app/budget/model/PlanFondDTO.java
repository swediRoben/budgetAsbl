package com.app.budget.model;

import com.app.budget.domain.Classe;
import com.app.budget.domain.Fonctionnaire;
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
    private Long idClasse;
    private Long responsableId;

    private ProjetDTO projet;
    private ExerciceDTO exercice;
    private CategorieDTO categorie;
    private ClasseDTO  classe;
    private Fonctionnaire responsable;
 
    private BigDecimal montant;

}
