package com.app.budget.repos;

import com.app.budget.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    Categorie findFirstByProjetIdId(Long id);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

}
