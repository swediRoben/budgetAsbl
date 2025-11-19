package com.app.budget.repos;

import com.app.budget.domain.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;


public interface ProjetRepository extends JpaRepository<Projet, Long> {

    boolean existsByCodeIgnoreCase(String code);
    boolean existsByLibelleIgnoreCase(String libelle);
    List<Projet> findByLibelleAndDateCreatedBetween(String libelle, OffsetDateTime dateCreated, OffsetDateTime dateCreated2);

}
