package com.app.budget.repos;

import com.app.budget.domain.PlanActivite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PlanActiviteRepository extends JpaRepository<PlanActivite, Long> {


    @Query("SELECT p FROM PlanActivite p " +
       "WHERE (:exercice IS NULL OR p.idExercice = :exercice) " +
       "AND (:projet IS NULL OR p.idProjet = :projet) " +
       "AND (:categorie IS NULL OR p.idCategorie = :categorie) " +
       "AND (:classe IS NULL OR p.idClasse = :classe) " +
       "AND (:source IS NULL OR p.idSource = :source)")
List<PlanActivite> findAllByAllData(
        @Param("exercice") Long exercice, 
        @Param("projet") Long projet, 
        @Param("categorie") Long categorie, 
        @Param("classe") Long classe, 
        @Param("source") Long source
);


}
