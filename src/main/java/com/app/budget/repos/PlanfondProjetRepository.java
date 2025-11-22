package com.app.budget.repos;

import com.app.budget.domain.PlanfondProjet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PlanfondProjetRepository extends JpaRepository<PlanfondProjet, Long> {
    List<PlanfondProjet> findByExerciceId_Id(Long id);
}
