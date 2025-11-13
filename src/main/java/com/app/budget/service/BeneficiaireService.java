package com.app.budget.service;

import com.app.budget.domain.Beneficiaire;
import com.app.budget.model.BeneficiaireDTO;
import com.app.budget.repos.BeneficiaireRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;

    public BeneficiaireService(final BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    public List<BeneficiaireDTO> findAll() {
        final List<Beneficiaire> beneficiaires = beneficiaireRepository.findAll(Sort.by("id"));
        return beneficiaires.stream()
                .map(beneficiaire -> mapToDTO(beneficiaire, new BeneficiaireDTO()))
                .toList();
    }

    public BeneficiaireDTO get(final Long id) {
        return beneficiaireRepository.findById(id)
                .map(beneficiaire -> mapToDTO(beneficiaire, new BeneficiaireDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BeneficiaireDTO beneficiaireDTO) {
        final Beneficiaire beneficiaire = new Beneficiaire();
        mapToEntity(beneficiaireDTO, beneficiaire);
        return beneficiaireRepository.save(beneficiaire).getId();
    }

    public void update(final Long id, final BeneficiaireDTO beneficiaireDTO) {
        final Beneficiaire beneficiaire = beneficiaireRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(beneficiaireDTO, beneficiaire);
        beneficiaireRepository.save(beneficiaire);
    }

    public void delete(final Long id) {
        final Beneficiaire beneficiaire = beneficiaireRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        beneficiaireRepository.delete(beneficiaire);
    }

    private BeneficiaireDTO mapToDTO(final Beneficiaire beneficiaire,
            final BeneficiaireDTO beneficiaireDTO) {
        beneficiaireDTO.setId(beneficiaire.getId());
        beneficiaireDTO.setLibelle(beneficiaire.getLibelle());
        return beneficiaireDTO;
    }

    private Beneficiaire mapToEntity(final BeneficiaireDTO beneficiaireDTO,
            final Beneficiaire beneficiaire) {
        beneficiaire.setLibelle(beneficiaireDTO.getLibelle());
        return beneficiaire;
    }

}
