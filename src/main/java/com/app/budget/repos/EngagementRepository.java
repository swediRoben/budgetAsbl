package com.app.budget.repos;

import com.app.budget.domain.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EngagementRepository extends JpaRepository<Engagement, Long> {
}
