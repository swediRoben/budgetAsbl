package com.app.budget.model;
 
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import com.app.budget.domain.Devise;
import com.app.budget.domain.Fonctionnaire;
import com.app.budget.domain.PlanActivite;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LiquidationDTO { 
    private Long id; 
    private Long idEngagement; 
    private String bonEngagment; 
    private String piece;
    private Long idExercice; 
    private Long idProjet; 
    private Long idCategorie; 
    private Long idPlanFondActivite;
    private Long idResponsable;  
    private Long idDevise;
    private BigDecimal tauxDevise;
    private BigDecimal montant;
    private String objet;
    
    private Boolean enAttente;
    private OffsetDateTime dataEnAttente;
    private Boolean validation;
    private OffsetDateTime dataValidation;
    private Boolean reception;
    private OffsetDateTime dataReception;
    private Boolean retourner;
    private OffsetDateTime dataRetourner;
    private Boolean rejet;
    private OffsetDateTime dataRejet;
    private String observation; 
    private Fonctionnaire responsable;
    private Devise devise;
    private PlanActivite planActivite; 
    private OffsetDateTime dataPayer;
    private Boolean payer; 
}
