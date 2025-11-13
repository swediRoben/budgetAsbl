package com.app.budget.repos;

import com.app.budget.domain.SourceFinacement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SourceFinacementRepository extends JpaRepository<SourceFinacement, Long> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

}
