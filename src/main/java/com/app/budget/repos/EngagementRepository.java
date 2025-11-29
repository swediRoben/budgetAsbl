package com.app.budget.repos;

import com.app.budget.domain.Engagement;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EngagementRepository extends JpaRepository<Engagement, Long> {

        @Query("SELECT e FROM Engagement e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (:validation IS NULL OR e.validation = :validation) " +
           "AND (:debut IS NULL OR e.dateCreated >= :debut) " +
           "AND (:fin IS NULL OR e.dateCreated <= :fin)")
    Page<Engagement> findAllBy(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie,
            @Param("validation") Boolean validation,
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

@Query("SELECT COALESCE(SUM(e.montantPrevision), 0) FROM Engagement e " +
       "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
       "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
       "AND e.validation = false")
BigDecimal sumMontantNotAnnuler(
        @Param("exercice") Long exercice,
        @Param("projet") Long projet
);

}
