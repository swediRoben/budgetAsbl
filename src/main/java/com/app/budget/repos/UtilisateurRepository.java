package com.app.budget.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.budget.domain.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);
}