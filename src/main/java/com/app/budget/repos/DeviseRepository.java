package com.app.budget.repos;

import com.app.budget.domain.Devise;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeviseRepository extends JpaRepository<Devise, Long> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

    boolean existsBySymboleIgnoreCase(String symbole);

}
