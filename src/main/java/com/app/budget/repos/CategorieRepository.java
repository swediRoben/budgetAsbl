package com.app.budget.repos;

import com.app.budget.domain.Categorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    Categorie findFirstByProjetIdId(Long id);

    List<Categorie> findAllByProjetIdId(Long projetIdId);
 

    boolean existsByCodeAndProjetId_Id(String code, Long idcate);

    @Query("""
    SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
    FROM Categorie c
    WHERE c.libelle = :libelle
    AND c.projetId.id = :projetId
""")
    boolean existsByLibelleAndProjetId(
         @Param("libelle") String libelle,
          @Param("projetId") Long idprojet);

    boolean existsByCodeAndProjetId_IdAndIdNot(String code, Long idprojet, Long id);
@Query("""
    SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
    FROM Categorie c
    WHERE c.libelle = :libelle
    AND c.projetId.id = :projetId
    AND c.id <> :id
""")
    boolean existsByLibelleAndProjetIdAndIdNot(
         @Param("libelle") String libelle,
          @Param("projetId") Long idprojet,
           @Param("id") Long id);

}
