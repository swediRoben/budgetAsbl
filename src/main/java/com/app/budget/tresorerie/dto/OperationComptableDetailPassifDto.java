package com.app.budget.tresorerie.dto;

import com.app.budget.constate.TypeOperation;
import com.app.budget.domain.PlanComptable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationComptableDetailPassifDto {
    private Long id;
    private Long debitid;
    private Long creditid;
    private TypeOperation typeOperation;
    private PlanComptable debit;
    private PlanComptable credit;
    private OperationComptableDto operationComptable;
}
