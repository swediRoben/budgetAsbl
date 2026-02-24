package com.app.budget.tresorerie.service;
 

import com.app.budget.domain.PlanComptable;
import com.app.budget.repos.PlanComptableRepository;
import com.app.budget.tresorerie.dto.BanqueDTO;
import com.app.budget.tresorerie.entity.Banque; 
import com.app.budget.tresorerie.repository.BanqueRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BanqueService {

    private final BanqueRepository banqueRepository;
    private final PlanComptableRepository compteRepository; 
     

    // CREATE
    public BanqueDTO create(BanqueDTO dto) {
        Banque banque = new Banque();
        banque.setLibelle(dto.getLibelle());
        banque.setActif(dto.getActif());

        if(dto.getIdCompteComptable() != null){
            PlanComptable compte = compteRepository.findById(dto.getIdCompteComptable())
                    .orElseThrow(() -> new RuntimeException("CompteComptable non trouvé"));
            banque.setCompteComptable(compte);
        }

        banque = banqueRepository.save(banque);
        dto.setId(banque.getId());
        return dto;
    }

    // READ ALL avec filtres optionnels
    public List<BanqueDTO> getAll(String libelle, Boolean actif) {
        return banqueRepository.findByOptionalLibelleAndActif(libelle, actif).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // READ BY ID
    public BanqueDTO getById(Long id) {
        Banque b = banqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banque non trouvé"));
        return convertToDTO(b);
    }

    // UPDATE
    public BanqueDTO update(Long id, BanqueDTO dto) {
        Banque banque = banqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banque non trouvé"));

        banque.setLibelle(dto.getLibelle());
        banque.setActif(dto.getActif());

        if(dto.getIdCompteComptable() != null){
            PlanComptable compte = compteRepository.findById(dto.getIdCompteComptable())
                    .orElseThrow(() -> new RuntimeException("CompteComptable non trouvé"));
            banque.setCompteComptable(compte);
        }

        banqueRepository.save(banque);
        return dto;
    }

    // DELETE
    public void delete(Long id) {
        banqueRepository.deleteById(id);
    }

    private BanqueDTO convertToDTO(Banque b){
        BanqueDTO dto = new BanqueDTO();
        dto.setId(b.getId());
        dto.setLibelle(b.getLibelle());
        dto.setActif(b.getActif());
        dto.setIdCompteComptable(b.getCompteComptable() != null ? b.getCompteComptable().getId() : null);
        return dto;
    }
}

