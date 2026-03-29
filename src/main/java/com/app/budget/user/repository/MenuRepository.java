package com.app.budget.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.budget.user.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByIdRole(Long idRole);

    List<Menu> findByRole_Id(Long roleId);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.details WHERE m.role.id = :roleId")
    List<Menu> findMenusWithSousMenus(@Param("roleId") Long roleId);

}
