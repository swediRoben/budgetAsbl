package com.app.budget.repos;

import com.app.budget.domain.Classe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClasseRepository extends JpaRepository<Classe, Long> {

    boolean existsByLibelleIgnoreCase(String libelle);

}
