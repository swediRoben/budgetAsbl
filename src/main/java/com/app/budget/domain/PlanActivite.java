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
@Table(name = "PlanActivites")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PlanActivite {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column
    private Long idExercice; 

    @Column(name = "id_projets")
    private Long idProjet;

    @Column(name = "id_categorie")
    private Long idCategorie;
 
    @Column(name = "id_activite")
    private Long idActivite;
 
    @Column(name = "id_source")
    private Long idSource; 
    
    @Column(name = "id_classe")
    private Long idClasse;

    @Column(name = "id_benefice")
    private Long idBeneficiaire;

    @Column(name = "ligne_budgetaire")
    private String ligneBudgetaire;

    @Column(name = "unite_mesure")
    private String uniteMesure;

    @Column(name = "id_plancomptable")
    private Long idPlancomptable;

    @Column
    private Long quantite;
 
    private BigDecimal prixUnitaire;
 
    private BigDecimal montant;

    @Column(name = "date_debut")
    private OffsetDateTime debut;

    @Column(name = "date_fin")
    private OffsetDateTime fin;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;


@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_projets", referencedColumnName = "id", insertable = false, updatable = false)
private Projet projet; 

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_categorie", referencedColumnName = "id", insertable = false, updatable = false)
private Categorie categorie;
 

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_activite", referencedColumnName = "id", insertable = false, updatable = false)
private Activite activite;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_source", referencedColumnName = "id", insertable = false, updatable = false)
private SourceFinacement source; 

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_classe", referencedColumnName = "id", insertable = false, updatable = false)
private Classe classe;
 
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_benefice", referencedColumnName = "id", insertable = false, updatable = false)
private Beneficiaire beneficiaire;

 
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_plancomptable", referencedColumnName = "id", insertable = false, updatable = false)
private PlanComptable planComptable;

}
