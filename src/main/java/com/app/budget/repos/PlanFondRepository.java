package com.app.budget.repos;

import com.app.budget.domain.PlanFond; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PlanFondRepository extends JpaRepository<PlanFond, Long> {
    List<PlanFond> findByProjetId_IdOrExerciceId_Id(Long projetIdId, Long exerciceIdId);

    List<PlanFond> findByProjetId_IdAndExerciceId_Id(Long projetIdId, Long exerciceIdId);

 
@Query("""
    SELECT pf 
    FROM PlanFond pf 
    WHERE pf.projetId.id = :projet 
      AND pf.exerciceId.id = :exercice 
      AND pf.responsableId.id = :fonctionnaire
""")
List<PlanFond> findByProjetExerciceResponsable(
    @Param("projet") Long projet, 
    @Param("exercice") Long exercice, 
    @Param("fonctionnaire") Long fonctionnaire
);

 

}
