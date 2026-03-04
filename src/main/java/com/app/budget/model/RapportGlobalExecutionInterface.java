package com.app.budget.model;

import java.math.BigDecimal;

import com.app.budget.domain.PlanActivite;

public interface RapportGlobalExecutionInterface {
    PlanActivite getPlanactivite();
    BigDecimal getSomme();
}
