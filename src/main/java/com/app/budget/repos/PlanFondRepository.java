package com.app.budget.repos;

import com.app.budget.domain.PlanFond;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanFondRepository extends JpaRepository<PlanFond, Long> {

    List<PlanFond> findByIdExerciceAndIdProjet(Long exercice, Long projet);
}
