package com.app.budget.repos;

import com.app.budget.domain.Classe;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClasseRepository extends JpaRepository<Classe, Long> {

    boolean existsByLibelleIgnoreCase(String libelle);

    Optional<Classe> findByLibelle(String stringCellValue);

}
