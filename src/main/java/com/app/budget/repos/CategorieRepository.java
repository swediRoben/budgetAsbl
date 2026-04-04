package com.app.budget.repos;

import com.app.budget.domain.Categorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    Categorie findFirstByProjetIdId(Long id);

    List<Categorie> findAllByProjetIdId(Long projetIdId);
 
    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

    boolean existsByCodeAndProjetId_Id(String code, Long idcate);

    boolean existsByLibelleAndProjetId_Id(String libelle, Long idcate);

    boolean existsByCodeAndProjetId_IdAndIdNot(String code, Long idprojet, Long id);

    boolean existsByLibelleAndProjetId_IdAndIdNot(String libelle, Long idprojet, Long id);

}
