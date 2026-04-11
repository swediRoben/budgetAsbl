package com.app.budget.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Liquidations")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Liquidation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "engagement_id") 
    private Long idEngagement;

    @Column
    private String bonEngagment;
    private String piece;

    @Column
    private Long idExercice;

    @Column(name = "id_projets")
    private Long idProjet;
    
    @Column(name = "id_categorie")
    private Long idCategorie;
    @Column(name = "id_planFond_activite")
    private Long idPlanFondActivite;
    @Column(name = "id_responsable") 
    private Long idResponsable; 
    @Column(name = "id_devise") 
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
    private OffsetDateTime dataPayer;
    private Boolean payer; 
    private String observation; 
    
  
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_responsable", referencedColumnName = "id", insertable = false, updatable = false)
    private Fonctionnaire responsable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_devise", referencedColumnName = "id", insertable = false, updatable = false)
    private Devise devise;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_planFond_activite", referencedColumnName = "id", insertable = false, updatable = false)
    private PlanActivite planActivite; 
}
