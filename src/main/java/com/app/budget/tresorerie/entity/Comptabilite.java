package com.app.budget.tresorerie.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.budget.constate.TypeJournal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comptabilite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comptabilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objet;
    private OffsetDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_banque")
    private Banque banque;

    @ManyToOne
    @JoinColumn(name = "id_compte_bancaire")
    private CompteBancaire compteBancaire;

    private Long idExerice;

    @Enumerated(EnumType.STRING)
    private TypeJournal type;

    @OneToMany(mappedBy = "ecriture", cascade = CascadeType.ALL)
    private List<LigneComptable> lignes = new ArrayList<>();
}