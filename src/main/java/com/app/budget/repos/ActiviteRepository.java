package com.app.budget.repos;

import com.app.budget.domain.Activite;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActiviteRepository extends JpaRepository<Activite, Long> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

}
