package com.app.budget.model;

import java.math.BigDecimal;

import com.app.budget.domain.PlanActivite;

import lombok.Data;

@Data
public class RapportGlobalExecution {
    private PlanActivite planActivite;
    private BigDecimal montantEngage;
    private BigDecimal montantLiquide;
}
