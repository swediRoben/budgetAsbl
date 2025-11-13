package com.app.budget.repos;

import com.app.budget.domain.Liquidation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LiquidationRepository extends JpaRepository<Liquidation, Long> {

    Liquidation findFirstByEngagementIdId(Long id);

}
