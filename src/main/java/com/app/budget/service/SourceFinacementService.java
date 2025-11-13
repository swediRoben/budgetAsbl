package com.app.budget.service;

import com.app.budget.domain.SourceFinacement;
import com.app.budget.model.SourceFinacementDTO;
import com.app.budget.repos.SourceFinacementRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SourceFinacementService {

    private final SourceFinacementRepository sourceFinacementRepository;

    public SourceFinacementService(final SourceFinacementRepository sourceFinacementRepository) {
        this.sourceFinacementRepository = sourceFinacementRepository;
    }

    public List<SourceFinacementDTO> findAll() {
        final List<SourceFinacement> sourceFinacements = sourceFinacementRepository.findAll(Sort.by("id"));
        return sourceFinacements.stream()
                .map(sourceFinacement -> mapToDTO(sourceFinacement, new SourceFinacementDTO()))
                .toList();
    }

    public SourceFinacementDTO get(final Long id) {
        return sourceFinacementRepository.findById(id)
                .map(sourceFinacement -> mapToDTO(sourceFinacement, new SourceFinacementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SourceFinacementDTO sourceFinacementDTO) {
        final SourceFinacement sourceFinacement = new SourceFinacement();
        mapToEntity(sourceFinacementDTO, sourceFinacement);
        return sourceFinacementRepository.save(sourceFinacement).getId();
    }

    public void update(final Long id, final SourceFinacementDTO sourceFinacementDTO) {
        final SourceFinacement sourceFinacement = sourceFinacementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sourceFinacementDTO, sourceFinacement);
        sourceFinacementRepository.save(sourceFinacement);
    }

    public void delete(final Long id) {
        final SourceFinacement sourceFinacement = sourceFinacementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        sourceFinacementRepository.delete(sourceFinacement);
    }

    private SourceFinacementDTO mapToDTO(final SourceFinacement sourceFinacement,
            final SourceFinacementDTO sourceFinacementDTO) {
        sourceFinacementDTO.setId(sourceFinacement.getId());
        sourceFinacementDTO.setCode(sourceFinacement.getCode());
        sourceFinacementDTO.setLibelle(sourceFinacement.getLibelle());
        sourceFinacementDTO.setIdTypeSourcefinancement(sourceFinacement.getIdTypeSourcefinancement());
        return sourceFinacementDTO;
    }

    private SourceFinacement mapToEntity(final SourceFinacementDTO sourceFinacementDTO,
            final SourceFinacement sourceFinacement) {
        sourceFinacement.setCode(sourceFinacementDTO.getCode());
        sourceFinacement.setLibelle(sourceFinacementDTO.getLibelle());
        sourceFinacement.setIdTypeSourcefinancement(sourceFinacementDTO.getIdTypeSourcefinancement());
        return sourceFinacement;
    }

    public boolean codeExists(final String code) {
        return sourceFinacementRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return sourceFinacementRepository.existsByLibelleIgnoreCase(libelle);
    }

}
