package com.app.budget.repos;

import com.app.budget.domain.Liquidation;

import org.springframework.data.jpa.repository.JpaRepository;  
 

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LiquidationRepository extends JpaRepository<Liquidation, Long> {
 
        @Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.enAttente=true OR e.retourner=true ) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Liquidation> findAllEntenteEtRetourner(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

     @Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.enAttente=true OR e.reception=true ) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Liquidation> findAllEntenteEtReceptioner(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

    
     @Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.validation=true OR e.rejet=true ) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Liquidation> findAllRejeterEtValider(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 

         @Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " +
           "AND (:activite IS NULL OR e.planActivite.idActivite = :activite) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.validation=true) " + 
           "AND (:debut IS NULL OR e.dataEnAttente >= :debut) " +
           "AND (:fin IS NULL OR e.dataEnAttente <= :fin)")
    Page<Liquidation> findAllValider(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("activite") Long activite,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    );
 

@Query("SELECT COALESCE(SUM(e.montant*e.tauxDevise), 0) FROM Liquidation e " +
       "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
       "AND (:engagement IS NULL OR e.idEngagement = :engagement) " +
       "AND e.rejet = false ")
BigDecimal sumMontantNotAnnuler(
        @Param("exercice") Long exercice,
        @Param("engagement") Long engagement
);

Optional<Liquidation> findByIdAndValidation(Long id, boolean b);

Optional<Liquidation> findByIdAndRejet(Long id, boolean b);

Optional<Liquidation> findByIdAndReception(Long id, boolean b);

Optional<Liquidation> findByIdAndRetourner(Long id, boolean b);


@Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " +  
           "AND (e.validation=true) " +  
           "AND (:debut IS NULL OR :fin IS NULL OR e.dataValidation BETWEEN :debut AND :fin)"
        )
Page<Liquidation> getAllValider(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet, 
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    );

@Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " +
           "AND (e.rejet=true) " + 
          "AND (:debut IS NULL OR :fin IS NULL OR e.dataRejet BETWEEN :debut AND :fin)"
        )
Page<Liquidation> getAllRejeter(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    );


@Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.retourner=true  ) " + 
           "AND (:debut IS NULL OR :fin IS NULL OR e.dataRetourner BETWEEN :debut AND :fin)"
            )
Page<Liquidation> getAllRetourner(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet, 
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    );

@Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.enAttente=true) " + 
           "AND (:debut IS NULL OR :fin IS NULL OR e.dataEnAttente BETWEEN :debut AND :fin)"
          )
Page<Liquidation> getAllAttenter(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet, 
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    );

@Query("SELECT e FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.idProjet = :projet) " + 
           "AND (:categorie IS NULL OR e.planActivite.idCategorie = :categorie) " + 
           "AND (e.reception=true) " + 
           "AND (:debut IS NULL OR :fin IS NULL OR e.dataReception BETWEEN :debut AND :fin)"
          )
Page<Liquidation> getAllReceptionner(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet, 
            @Param("categorie") Long categorie, 
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin,
            Pageable pageable
    ); 
    

 @Query("SELECT COALESCE(SUM(e.montant*e.tauxDevise), 0) FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:activite IS NULL OR e.planActivite.id = :activite) " +
           "AND (e.validation=true) ") 
BigDecimal rapporGeneralLiquidation(
            @Param("exercice") Long exercice,
            @Param("activite") Long activite
    );


 @Query("SELECT COUNT(e) FROM Liquidation e " +
           "WHERE (:exercice IS NULL OR e.idExercice = :exercice) " +
           "AND (:projet IS NULL OR e.planActivite.idProjet = :projet) "+
           "AND (:valide IS NULL OR e.validation = :valide) "+
           "AND (:rejet IS NULL OR e.rejet = :rejet) "+
           "AND (:reception IS NULL OR e.reception = :reception) "+
           "AND (:retourne IS NULL OR e.retourner = :retourne) "+
           "AND (:entante IS NULL OR e.enAttente = :entante) " )
  Integer countLiquidation(
            @Param("exercice") Long exercice,
            @Param("projet") Long projet,
            @Param("entante") Boolean entante,
            @Param("reception") Boolean reception,
            @Param("valide") Boolean valide,
            @Param("rejet") Boolean rejet, 
            @Param("retourne") Boolean retourne
    );  
}

