package com.app.budget.structure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.budget.structure.entity.Structure;

public interface StructureRepository extends JpaRepository<Structure, Long> {
}