package com.app.budget.repos;

import com.app.budget.domain.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {
}
