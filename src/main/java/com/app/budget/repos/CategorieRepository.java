package com.app.budget.repos;

import com.app.budget.domain.Categorie;
import com.app.budget.domain.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    Categorie findFirstByProjetIdId(Long id);

    List<Categorie> findAllByProjetIdId(Long projetIdId);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

}
