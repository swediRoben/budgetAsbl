package com.app.budget.tresorerie.entity;

import com.app.budget.domain.PlanComptable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "operation_comptable_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationComptableDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @ManyToOne
    @JoinColumn(name = "compte_debut_id")
    private PlanComptable debit;
    @ManyToOne
    @JoinColumn(name = "compte_credit_id")
    private PlanComptable credit;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationComptable operationComptable;
}
