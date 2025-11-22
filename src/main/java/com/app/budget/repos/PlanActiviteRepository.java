package com.app.budget.repos;

import com.app.budget.domain.PlanActivite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PlanActiviteRepository extends JpaRepository<PlanActivite, Long> {

   @Query("SELECT d FROM PlanActivite d WHERE "+
    "(d.idExercice=:exercice OR d.idExercice IS NULL)"+
    "AND (d.idProjet=:projet OR d.idProjet IS NULL) "+
    "AND (d.idCategorie=:categorie OR d.idCategorie IS NULL) ")
    List<PlanActivite> findData(@Param("exercice") Long exercice,@Param("projet") Long projet,@Param("categorie") Long categorie);
}
  