package com.app.budget.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.app.budget.domain.CompteurBE;
import com.app.budget.repos.CompteurBERepository;

import jakarta.transaction.Transactional;

@Service
public class BonEngagementNumeroService {

    private final CompteurBERepository compteurBERepository;

    public BonEngagementNumeroService(CompteurBERepository compteurBERepository) {
        this.compteurBERepository = compteurBERepository;
    }

    @Transactional
    public String generateNumero(OffsetDateTime dateBe) {

        int annee = dateBe.getYear();

        CompteurBE compteur = compteurBERepository.findByAnneeForUpdate(annee)
                .orElseGet(() -> {
                    CompteurBE c = new CompteurBE();
                    c.setAnnee(annee);
                    c.setLastNumber(0L);
                    return c;
                });

        long next = compteur.getLastNumber() + 1;
        compteur.setLastNumber(next);

        compteurBERepository.save(compteur);

        return String.format("BE-%d-%06d", annee, next);
    }
}
