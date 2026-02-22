package com.app.budget.tresorerie.repository; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.budget.tresorerie.entity.CompteBancaire;

import java.util.List;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {

    @Query("SELECT c FROM CompteBancaire c " +
           "WHERE (:banqueId IS NULL OR c.banque.id = :banqueId) " +
           "AND (:numero IS NULL OR LOWER(c.numero) LIKE LOWER(CONCAT('%', :numero, '%'))) " +
           "AND (:idDevise IS NULL OR c.idDevise = :idDevise)")
    List<CompteBancaire> findByOptionalFilters(@Param("banqueId") Long banqueId,
                                                @Param("numero") String numero,
                                                @Param("idDevise") Long idDevise);
}
