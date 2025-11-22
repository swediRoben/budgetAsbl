package com.app.budget.repos;

import com.app.budget.domain.PlanfondProjet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanfondProjetRepository extends JpaRepository<PlanfondProjet, Long> {

    List<PlanfondProjet> findByIdExercice(Long exercice);
}
