package com.app.budget.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.budget.user.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    List<Users> findByActif(boolean actif);

    List<Users> findByIdRole(Long idRole);
}
