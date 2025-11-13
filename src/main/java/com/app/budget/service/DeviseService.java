package com.app.budget.service;

import com.app.budget.domain.Devise;
import com.app.budget.model.DeviseDTO;
import com.app.budget.repos.DeviseRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DeviseService {

    private final DeviseRepository deviseRepository;

    public DeviseService(final DeviseRepository deviseRepository) {
        this.deviseRepository = deviseRepository;
    }

    public List<DeviseDTO> findAll() {
        final List<Devise> devises = deviseRepository.findAll(Sort.by("id"));
        return devises.stream()
                .map(devise -> mapToDTO(devise, new DeviseDTO()))
                .toList();
    }

    public DeviseDTO get(final Long id) {
        return deviseRepository.findById(id)
                .map(devise -> mapToDTO(devise, new DeviseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DeviseDTO deviseDTO) {
        final Devise devise = new Devise();
        mapToEntity(deviseDTO, devise);
        return deviseRepository.save(devise).getId();
    }

    public void update(final Long id, final DeviseDTO deviseDTO) {
        final Devise devise = deviseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(deviseDTO, devise);
        deviseRepository.save(devise);
    }

    public void delete(final Long id) {
        final Devise devise = deviseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        deviseRepository.delete(devise);
    }

    private DeviseDTO mapToDTO(final Devise devise, final DeviseDTO deviseDTO) {
        deviseDTO.setId(devise.getId());
        deviseDTO.setCode(devise.getCode());
        deviseDTO.setLibelle(devise.getLibelle());
        deviseDTO.setSymbole(devise.getSymbole());
        deviseDTO.setActif(devise.getActif());
        return deviseDTO;
    }

    private Devise mapToEntity(final DeviseDTO deviseDTO, final Devise devise) {
        devise.setCode(deviseDTO.getCode());
        devise.setLibelle(deviseDTO.getLibelle());
        devise.setSymbole(deviseDTO.getSymbole());
        devise.setActif(deviseDTO.getActif());
        return devise;
    }

    public boolean codeExists(final String code) {
        return deviseRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return deviseRepository.existsByLibelleIgnoreCase(libelle);
    }

    public boolean symboleExists(final String symbole) {
        return deviseRepository.existsBySymboleIgnoreCase(symbole);
    }

}
