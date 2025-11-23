package com.app.budget.repos;

import com.app.budget.domain.PlanFond;
import com.app.budget.domain.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PlanFondRepository extends JpaRepository<PlanFond, Long> {
    List<PlanFond> findByProjetId_IdOrExerciceId_Id(Long projetIdId, Long exerciceIdId);

    List<PlanFond> findByProjetId_IdAndExerciceId_Id(Long projetIdId, Long exerciceIdId);
}
