package com.app.budget.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.budget.domain.CompteurBE;

import jakarta.persistence.LockModeType;

public interface CompteurBERepository extends JpaRepository<CompteurBE, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CompteurBE c WHERE c.annee = :annee")
    Optional<CompteurBE> findByAnneeForUpdate(@Param("annee") Integer annee);
}

