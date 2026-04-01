package com.app.budget.tresorerie.dto;

import com.app.budget.domain.PlanComptable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationComptableDetailDto {
    private Long id;
    private Long debitid;
    private Long creditid;
    private PlanComptable debit;
    private PlanComptable credit;
    private OperationComptableDto operationComptable;
}
