package com.app.budget.repos;

import com.app.budget.domain.Activite; 
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActiviteRepository extends JpaRepository<Activite, Long> {

    boolean existsByCodeIgnoreCase(String code);

//    List<Activite> findAllByCategorieIdProjetId_Id(Long categorieIdId);
    List<Activite> findAllByCategorieIdProjetId_IdOrCategorieId_Id(Long idProjetId, Long idCategorieId);

    List<Activite> findAllByCategorieId_ProjetId_IdOrCategorieId_Id(Long idProjetId, Long idCategorieId);

    boolean existsByLibelleIgnoreCase(String libelle);

    boolean existsByCodeAndCategorieId_IdAndIdNot(String code,Long idcate, Long id);

    boolean existsByLibelleAndCategorieId_IdAndIdNot(String libelle,Long idcate, Long id);

    boolean existsByCodeAndCategorieId_Id(String code,Long idcate);

    boolean existsByLibelleAndCategorieId_Id(String libelle,Long idcate);

}
