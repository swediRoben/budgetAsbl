package com.app.budget.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "PlanfondProjets")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PlanfondProjet {

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

//    @Column
//    private Long idProjet;
//
//    @Column
//    private Long idExercice;
//
//    @Column
//    private Long idSource;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projet_id")
    private Projet projetId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercice_id")
    private Exercice exerciceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sourceFinacement_id")
    private SourceFinacement sourceFinacement;

    @Column(precision = 10, scale = 2)
    private BigDecimal montant;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
