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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data; 
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Engagements")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Engagement {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String bonEngagement;

    @Column
    private Long idExercice;

    @Column
    private String ligneBudgetaire;

    @Column(name = "id_planFond_activite")
    private Long idPlanFondActivite;
    @Column(name = "id_projets")
    private Long idProjet;
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
    private String observation;   

    @Column(name = "date_created", nullable = false, updatable = false)
     @CreatedDate
     private OffsetDateTime dateCreated;
    
   @Column(name = "last_updated", nullable = false)
   @LastModifiedDate
   private OffsetDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_planFond_activite", referencedColumnName = "id", insertable = false, updatable = false)
    private PlanActivite planActivite; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_responsable", referencedColumnName = "id", insertable = false, updatable = false)
    private Fonctionnaire responsable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_devise", referencedColumnName = "id", insertable = false, updatable = false)
    private Devise devise;

}
