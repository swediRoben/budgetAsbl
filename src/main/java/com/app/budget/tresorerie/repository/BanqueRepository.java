package com.app.budget.tresorerie.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.budget.tresorerie.entity.Banque;
  

import java.util.List;
@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {

    @Query("SELECT b FROM Banque b " +
           "WHERE (:libelle IS NULL OR LOWER(b.libelle) LIKE LOWER(CONCAT('%', :libelle, '%'))) " +
           "AND (:actif IS NULL OR b.actif = :actif)")
    List<Banque> findByOptionalLibelleAndActif(@Param("libelle") String libelle,
                                               @Param("actif") Boolean actif);
}