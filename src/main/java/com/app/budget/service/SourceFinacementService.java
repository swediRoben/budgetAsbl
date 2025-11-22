package com.app.budget.service;

import com.app.budget.domain.SourceFinacement;
import com.app.budget.model.SourceMapper;
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
                .map(sourceFinacement -> SourceMapper.getInstance().mapToDTO(sourceFinacement))
                .toList();
    }

    public SourceFinacementDTO get(final Long id) {
        return sourceFinacementRepository.findById(id)
                .map(sourceFinacement -> SourceMapper.getInstance().mapToDTO(sourceFinacement))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SourceFinacementDTO sourceFinacementDTO) {
        final SourceFinacement sourceFinacement = new SourceFinacement();
        SourceMapper.getInstance().mapToEntity(sourceFinacementDTO);
        return sourceFinacementRepository.save( SourceMapper.getInstance().mapToEntity(sourceFinacementDTO)).getId();
    }

    public void update(final Long id, final SourceFinacementDTO sourceFinacementDTO) {
        final SourceFinacement sourceFinacement = sourceFinacementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        SourceMapper.getInstance().mapToEntity(sourceFinacementDTO);
        sourceFinacementRepository.save(SourceMapper.getInstance().mapToEntity(sourceFinacementDTO));
    }

    public void delete(final Long id) {
        final SourceFinacement sourceFinacement = sourceFinacementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        sourceFinacementRepository.delete(sourceFinacement);
    }
    public boolean codeExists(final String code) {
        return sourceFinacementRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return sourceFinacementRepository.existsByLibelleIgnoreCase(libelle);
    }

}
