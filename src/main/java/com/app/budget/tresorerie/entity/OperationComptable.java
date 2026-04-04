package com.app.budget.tresorerie.entity;

import java.util.ArrayList;
import java.util.List;

import com.app.budget.constate.TypeClasse;
import com.app.budget.domain.Classe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "operation_comptable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationComptable { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    @Enumerated(EnumType.STRING)
    private TypeClasse type; 
    @Column(name = "classe_id")
    private Long classeid;
    @ManyToOne
    @JoinColumn(name = "classe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Classe classe;
    @OneToMany(mappedBy = "operationComptable", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OperationComptableDetail> details=new ArrayList<>();
}
