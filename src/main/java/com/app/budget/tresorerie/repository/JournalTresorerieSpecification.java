package com.app.budget.tresorerie.repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.app.budget.tresorerie.dto.JournalTresorerieFilter;
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

 
    public static Specification<JournalTresorerie> withFilter(JournalTresorerieFilter f) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // exercice
            if (f.getExerciceId() != null) {
                predicates.add(cb.equal(root.get("idExercice"), f.getExerciceId()));
            }

            // date debut
            if (f.getDebut() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), f.getDebut()));
            }

            // date fin
            if (f.getFin() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), f.getFin()));
            }

            // reference like
            if (f.getReference() != null && !f.getReference().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("reference")),
                        "%" + f.getReference().toLowerCase() + "%"
                ));
            }

            // projet
            if (f.getProjetId() != null) {
                predicates.add(cb.equal(root.get("projetId"), f.getProjetId()));
            }

            // categorie
            if (f.getCategorieId() != null) {
                predicates.add(cb.equal(root.get("categorieId"), f.getCategorieId()));
            }

            // type mouvement
            if (f.getTypeMouvement() != null) {
                predicates.add(cb.equal(root.get("typemouvement"), f.getTypeMouvement()));
            }

            // source financement
            if (f.getSourceFinancementId() != null) {
                predicates.add(cb.equal(
                        root.get("sourceFinacement").get("id"),
                        f.getSourceFinancementId()
                ));
            }

            // banque
            if (f.getBanqueId() != null) {
                predicates.add(cb.equal(
                        root.get("banque").get("id"),
                        f.getBanqueId()
                ));
            }

            // compte bancaire
            if (f.getCompteBanqueId() != null) {
                predicates.add(cb.equal(
                        root.get("compteBancaire").get("id"),
                        f.getCompteBanqueId()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    } 

}