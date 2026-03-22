package com.app.budget.tresorerie.entity;

import java.util.List;

import com.app.budget.constate.TypeClasse;
import com.app.budget.domain.Classe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "operation_comptable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationComptable {
    private Long id;
    private String libelle;
    @Enumerated(EnumType.STRING)
    private TypeClasse type;
    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;
    @OneToMany(mappedBy = "operationComptable", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OperationComptableDetail> details;
}
