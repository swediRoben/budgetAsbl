package com.app.budget.tresorerie.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.budget.constate.TypeClasse;
import com.app.budget.tresorerie.entity.OperationComptable;

public interface OperationComptableRepositories extends JpaRepository<OperationComptable, Long> {

    List<OperationComptable> findAllByType(TypeClasse type);
    
}
