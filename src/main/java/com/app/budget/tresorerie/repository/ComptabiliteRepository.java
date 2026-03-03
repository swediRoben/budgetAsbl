package com.app.budget.tresorerie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.budget.tresorerie.entity.Comptabilite;
@Repository
public interface ComptabiliteRepository extends JpaRepository<Comptabilite, Long> {
    
}
