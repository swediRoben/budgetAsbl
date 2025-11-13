package com.app.budget.repos;

import com.app.budget.domain.Projet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjetRepository extends JpaRepository<Projet, Long> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

}
