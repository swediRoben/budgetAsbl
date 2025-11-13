package com.app.budget.repos;

import com.app.budget.domain.Exercice;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExerciceRepository extends JpaRepository<Exercice, Long> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByLibelleIgnoreCase(String libelle);

    boolean existsByDateDebut(LocalDate dateDebut);

    boolean existsByDateFin(LocalDate dateFin);

}
