package com.app.budget.tresorerie.service;
 
import org.springframework.stereotype.Service;

import com.app.budget.repos.SourceFinacementRepository;
import com.app.budget.tresorerie.dto.CompteBancaireDto;
import com.app.budget.tresorerie.entity.Banque;
import com.app.budget.tresorerie.entity.CompteBancaire;
import com.app.budget.tresorerie.repository.BanqueRepository;
import com.app.budget.tresorerie.repository.CompteBancaireRepository;
import com.app.budget.tresorerie.repository.JournalTresorerieRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompteBancaireService {

    private final CompteBancaireRepository compteRepository;
    private final BanqueRepository banqueRepository;
    private final SourceFinacementRepository sourceFinacementRepository; 
    private final JournalTresorerieRepository journalTresorerieRepository;

    public CompteBancaireService(CompteBancaireRepository compteRepository, BanqueRepository banqueRepository,
            SourceFinacementRepository sourceFinacementRepository,
            JournalTresorerieRepository journalTresorerieRepository) {
        this.compteRepository = compteRepository;
        this.banqueRepository = banqueRepository;
        this.sourceFinacementRepository = sourceFinacementRepository;
        this.journalTresorerieRepository = journalTresorerieRepository;
    }

    // CREATE
    public CompteBancaireDto create(CompteBancaireDto dto) {
        CompteBancaire compte = new CompteBancaire();
        compte.setNumero(dto.getNumero());
        compte.setIdDevise(dto.getIdDevise());
        compte.setIdComteComptable(dto.getIdComteComptable()); 
        compte.setTypeCompte(dto.getTypeCompte()); 
    if (dto.getSourceFinacementId() != null) {
        compte.setSourceFinacement(sourceFinacementRepository.findById(dto.getSourceFinacementId()).orElse(null));
        }
        if(dto.getIdBanque() != null){
            Banque banque = banqueRepository.findById(dto.getIdBanque())
                    .orElseThrow(() -> new RuntimeException("Banque non trouvée"));
            compte.setBanque(banque);
        }

        compte = compteRepository.save(compte);
        dto.setId(compte.getId());
        return dto;
    }

    // READ ALL avec filtres optionnels
    public List<CompteBancaireDto> getAll(Long banqueId, String numero, Long idDevise) {
        return compteRepository.findByOptionalFilters(banqueId, numero, idDevise).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // READ BY ID
    public CompteBancaireDto getById(Long id){
        CompteBancaire c = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompteBancaire non trouvé"));
        return convertToDTO(c);
    }

    // UPDATE
    public CompteBancaireDto update(Long id, CompteBancaireDto dto){
        CompteBancaire compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompteBancaire non trouvé"));

        compte.setNumero(dto.getNumero());
        compte.setIdDevise(dto.getIdDevise());
        compte.setIdComteComptable(dto.getIdComteComptable());
        compte.setTypeCompte(dto.getTypeCompte());  
        if (dto.getSourceFinacementId() != null) {
        compte.setSourceFinacement(sourceFinacementRepository.findById(dto.getSourceFinacementId()).orElse(null));
        }

        if(dto.getIdBanque() != null){
            Banque banque = banqueRepository.findById(dto.getIdBanque())
                    .orElseThrow(() -> new RuntimeException("Banque non trouvée"));
            compte.setBanque(banque);
        }

        compteRepository.save(compte);
        return dto;
    }

    // DELETE
    public void delete(Long id){
        compteRepository.deleteById(id);
    }

    private CompteBancaireDto convertToDTO(CompteBancaire c){
        CompteBancaireDto dto = new CompteBancaireDto();
        dto.setId(c.getId());
        dto.setNumero(c.getNumero());
        dto.setIdDevise(c.getIdDevise());
        dto.setIdBanque(c.getBanque() != null ? c.getBanque().getId() : null);
        dto.setIdComteComptable(c.getIdComteComptable());
        dto.setTypeCompte(c.getTypeCompte());
         dto.setSourceFinacementId(c.getSourceFinacement() != null ? c.getSourceFinacement().getId() : null);
         BigDecimal somme=journalTresorerieRepository.sommeCompteByIdComptebancaire(c.getId());
        BigDecimal montant=somme!=null?somme:BigDecimal.ZERO;
         dto.setMontant(montant); 
         return dto;
    }
}