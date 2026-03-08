package com.app.budget.tresorerie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.budget.tresorerie.entity.Comptabilite;

@Repository
public interface ComptabiliteRepository extends JpaRepository<Comptabilite, Long> {

    Optional<Comptabilite> findByIdtresorerie(Long idJournal);

    @Query("SELECT c FROM Comptabilite c LEFT JOIN FETCH c.lignes")
    List<Comptabilite> findAllWithLignes();
}
