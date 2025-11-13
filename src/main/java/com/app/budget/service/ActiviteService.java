package com.app.budget.service;

import com.app.budget.domain.Activite;
import com.app.budget.model.ActiviteDTO;
import com.app.budget.repos.ActiviteRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ActiviteService {

    private final ActiviteRepository activiteRepository;

    public ActiviteService(final ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }

    public List<ActiviteDTO> findAll() {
        final List<Activite> activites = activiteRepository.findAll(Sort.by("id"));
        return activites.stream()
                .map(activite -> mapToDTO(activite, new ActiviteDTO()))
                .toList();
    }

    public ActiviteDTO get(final Long id) {
        return activiteRepository.findById(id)
                .map(activite -> mapToDTO(activite, new ActiviteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ActiviteDTO activiteDTO) {
        final Activite activite = new Activite();
        mapToEntity(activiteDTO, activite);
        return activiteRepository.save(activite).getId();
    }

    public void update(final Long id, final ActiviteDTO activiteDTO) {
        final Activite activite = activiteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(activiteDTO, activite);
        activiteRepository.save(activite);
    }

    public void delete(final Long id) {
        final Activite activite = activiteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        activiteRepository.delete(activite);
    }

    private ActiviteDTO mapToDTO(final Activite activite, final ActiviteDTO activiteDTO) {
        activiteDTO.setId(activite.getId());
        activiteDTO.setCode(activite.getCode());
        activiteDTO.setLibelle(activite.getLibelle());
        return activiteDTO;
    }

    private Activite mapToEntity(final ActiviteDTO activiteDTO, final Activite activite) {
        activite.setCode(activiteDTO.getCode());
        activite.setLibelle(activiteDTO.getLibelle());
        return activite;
    }

    public boolean codeExists(final String code) {
        return activiteRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return activiteRepository.existsByLibelleIgnoreCase(libelle);
    }

}
