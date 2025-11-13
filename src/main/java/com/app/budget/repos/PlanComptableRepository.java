package com.app.budget.repos;

import com.app.budget.domain.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanComptableRepository extends JpaRepository<PlanComptable, Long> {

    boolean existsByLibelleIgnoreCase(String libelle);

    boolean existsByNumeroIgnoreCase(String numero);

}
