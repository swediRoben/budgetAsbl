package com.app.budget.repos;

import com.app.budget.domain.Engagement;
 

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

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
           "AND (e.enAttente=true OR e.retourner=true ) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Engagement> findAllEntenteEtRetourner(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

     @Query("SELECT e FROM Engagement e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.enAttente=true OR e.reception=true ) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Engagement> findAllEntenteEtReceptioner(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

    
     @Query("SELECT e FROM Engagement e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.validation=true OR e.rejet=true ) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Engagement> findAllRejeterEtValider(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

         @Query("SELECT e FROM Engagement e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.validation=true) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Engagement> findAllValider(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    );
 

@Query("SELECT COALESCE(SUM(e.montant), 0) FROM Engagement e " +
       "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
       "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
       "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) " +
       "AND e.rejet = false")
BigDecimal sumMontantNotAnnuler(
        @Param("exercice") Long exercice,
        @Param("projet") Long projet
);

Optional<Engagement> findByIdAndValidation(Long id, boolean b);

Optional<Engagement> findByIdAndRejet(Long id, boolean b);

Optional<Engagement> findByIdAndReception(Long id, boolean b);

Optional<Engagement> findByIdAndRetourner(Long id, boolean b);

}
