package com.app.budget.tresorerie.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.budget.tresorerie.entity.JournalTresorerie;

@Repository
public interface JournalTresorerieRepository extends
        JpaRepository<JournalTresorerie, Long>,
        JpaSpecificationExecutor<JournalTresorerie> {
   @Query("SELECT SUM(c.montant)-SUM(c.montant) FROM JournalTresorerie c WHERE c.compteBancaire.id=:idcompte AND  c.typemouvement IN ('CREDIT','DEBIT' ) ")
    BigDecimal sommeCompteByIdComptebancaire(@Param("idcompte") Long idcompte);
}
 