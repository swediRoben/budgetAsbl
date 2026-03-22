package com.app.budget.tresorerie.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.budget.constate.TypeClasse;
import com.app.budget.tresorerie.entity.OperationComptable;

public interface OperationComptableRepositories extends JpaRepository<OperationComptable, Long> {

    Collection<? extends OperationComptable> findAllByType(TypeClasse type);
    
}
