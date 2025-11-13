package com.app.budget.repos;

import com.app.budget.domain.TypSourceFinancement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypSourceFinancementRepository extends JpaRepository<TypSourceFinancement, Long> {

    boolean existsByLibelleIgnoreCase(String libelle);

}
