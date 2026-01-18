package com.app.budget.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.budget.domain.Fonctionnaire;
import com.app.budget.domain.RoleSysteme;

public interface FonctionnaireRepository extends JpaRepository<Fonctionnaire, Long> {

    List<Fonctionnaire> findByRoleSysteme(RoleSysteme responsable);
}
