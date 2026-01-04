package com.app.budget.model;
 
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LiquidationDTO {
    private Long id; 
    private Long engagementId;
    private String bonEngagement;

    private Long idExercice;
    private Long idProjet;
    private String ligneBudgetaire;
    private Long idPlanFondActivite;
    private Long idResponsable;
    private Long idDevise;
    private BigDecimal tauxDevise;
    private BigDecimal montant;
    private String numeroPiece;

    private Boolean enAttente;
    private OffsetDateTime dataEnAttente;
    private Boolean validation;
    private OffsetDateTime dataValidation;
    private Boolean reception;
    private OffsetDateTime dataReception;
    private Boolean rejet;
    private OffsetDateTime dataRejet;
    private String observation; 

}
