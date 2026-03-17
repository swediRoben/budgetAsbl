package com.app.budget.tresorerie.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.budget.constate.TypeJournal;
import com.app.budget.tresorerie.entity.Comptabilite;

@Repository
public interface ComptabiliteRepository extends JpaRepository<Comptabilite, Long> {

    Optional<Comptabilite> findByIdtresorerie(Long idJournal);

    @Query("SELECT c FROM Comptabilite c LEFT JOIN FETCH c.lignes")
    List<Comptabilite> findAllWithLignes();

    @Query("SELECT e FROM Comptabilite e " +
            "WHERE (:exercice IS NULL OR e.idExerice = :exercice) " +
            "AND (:type IS NULL OR e.type = :type) " +
            "AND (:banque IS NULL OR e.banque.id = :banque) " +
            "AND (:debut IS NULL OR :fin IS NULL OR e.date BETWEEN :debut AND :fin)")
    List<Comptabilite> findComptabilite(
            @Param("exercice") Long exercice,
            @Param("type") TypeJournal type,
            @Param("banque") Long banque,
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin);

    @Query("SELECT e FROM Comptabilite e LEFT JOIN FETCH e.lignes l " +
            "WHERE (:exercice IS NULL OR e.idExerice = :exercice) " +
            "AND (:type IS NULL OR e.type = :type) " +
            "AND (:banque IS NULL OR e.banque.id = :banque) " +
            "AND (:debut IS NULL OR :fin IS NULL OR e.date BETWEEN :debut AND :fin) " +
            "AND (:comptedebut IS NULL OR :comptefin IS NULL OR l.compte.numero BETWEEN :comptedebut AND :comptefin)")
    List<Comptabilite> findComptabiliteGrandLivre(
            @Param("exercice") Long exercice,
            @Param("type") TypeJournal type,
            @Param("banque") Long banque,
            @Param("comptedebut") String compteDebut,
            @Param("comptefin") String compteFin,
            @Param("debut") OffsetDateTime debut,
            @Param("fin") OffsetDateTime fin);

}
