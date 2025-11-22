package com.app.budget.repos;

import com.app.budget.domain.Activite;
import com.app.budget.domain.Categorie;
import com.app.budget.domain.Projet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActiviteRepository extends JpaRepository<Activite, Long> {

    boolean existsByCodeIgnoreCase(String code);

//    List<Activite> findAllByCategorieIdProjetId_Id(Long categorieIdId);
    List<Activite> findAllByCategorieIdProjetId_IdOrCategorieId_Id(Long idProjetId, Long idCategorieId);

    List<Activite> findAllByCategorieId_ProjetId_IdOrCategorieId_Id(Long idProjetId, Long idCategorieId);

    boolean existsByLibelleIgnoreCase(String libelle);

}
