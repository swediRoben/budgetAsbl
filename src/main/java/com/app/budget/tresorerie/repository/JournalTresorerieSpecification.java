package com.app.budget.tresorerie.repository;

import java.time.OffsetDateTime; 

import org.springframework.data.jpa.domain.Specification;

import com.app.budget.tresorerie.entity.JournalTresorerie;

import jakarta.persistence.criteria.Predicate;

public class JournalTresorerieSpecification {

    public static Specification<JournalTresorerie> filter(
            Long exerciceId,
            Long banqueId,
            Long compteBancaireId,
            String numero,
            OffsetDateTime debut,
            OffsetDateTime fin) {

        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (exerciceId != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("idExercice"), exerciceId));
            }

            if (banqueId != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("banque").get("id"), banqueId));
            }

            if (compteBancaireId != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("compteBancaire").get("id"), compteBancaireId));
            }

            if (numero != null && !numero.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("numero")),
                                "%" + numero.toLowerCase() + "%"));
            }

            if (debut != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(root.get("date"), debut));
            }

            if (fin != null) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(root.get("date"), fin));
            }

            return predicate;
        };
    }
}