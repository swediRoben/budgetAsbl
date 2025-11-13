package com.app.budget.service;

import com.app.budget.domain.TypSourceFinancement;
import com.app.budget.model.TypSourceFinancementDTO;
import com.app.budget.repos.TypSourceFinancementRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TypSourceFinancementService {

    private final TypSourceFinancementRepository typSourceFinancementRepository;

    public TypSourceFinancementService(
            final TypSourceFinancementRepository typSourceFinancementRepository) {
        this.typSourceFinancementRepository = typSourceFinancementRepository;
    }

    public List<TypSourceFinancementDTO> findAll() {
        final List<TypSourceFinancement> typSourceFinancements = typSourceFinancementRepository.findAll(Sort.by("id"));
        return typSourceFinancements.stream()
                .map(typSourceFinancement -> mapToDTO(typSourceFinancement, new TypSourceFinancementDTO()))
                .toList();
    }

    public TypSourceFinancementDTO get(final Long id) {
        return typSourceFinancementRepository.findById(id)
                .map(typSourceFinancement -> mapToDTO(typSourceFinancement, new TypSourceFinancementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TypSourceFinancementDTO typSourceFinancementDTO) {
        final TypSourceFinancement typSourceFinancement = new TypSourceFinancement();
        mapToEntity(typSourceFinancementDTO, typSourceFinancement);
        return typSourceFinancementRepository.save(typSourceFinancement).getId();
    }

    public void update(final Long id, final TypSourceFinancementDTO typSourceFinancementDTO) {
        final TypSourceFinancement typSourceFinancement = typSourceFinancementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(typSourceFinancementDTO, typSourceFinancement);
        typSourceFinancementRepository.save(typSourceFinancement);
    }

    public void delete(final Long id) {
        final TypSourceFinancement typSourceFinancement = typSourceFinancementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        typSourceFinancementRepository.delete(typSourceFinancement);
    }

    private TypSourceFinancementDTO mapToDTO(final TypSourceFinancement typSourceFinancement,
            final TypSourceFinancementDTO typSourceFinancementDTO) {
        typSourceFinancementDTO.setId(typSourceFinancement.getId());
        typSourceFinancementDTO.setLibelle(typSourceFinancement.getLibelle());
        return typSourceFinancementDTO;
    }

    private TypSourceFinancement mapToEntity(final TypSourceFinancementDTO typSourceFinancementDTO,
            final TypSourceFinancement typSourceFinancement) {
        typSourceFinancement.setLibelle(typSourceFinancementDTO.getLibelle());
        return typSourceFinancement;
    }

    public boolean libelleExists(final String libelle) {
        return typSourceFinancementRepository.existsByLibelleIgnoreCase(libelle);
    }

}
