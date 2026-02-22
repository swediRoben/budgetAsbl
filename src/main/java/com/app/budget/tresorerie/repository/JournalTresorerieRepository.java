package com.app.budget.tresorerie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.budget.tresorerie.entity.JournalTresorerie;

@Repository
public interface JournalTresorerieRepository extends
        JpaRepository<JournalTresorerie, Long>,
        JpaSpecificationExecutor<JournalTresorerie> {
}