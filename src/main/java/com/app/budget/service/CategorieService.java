package com.app.budget.service;

import com.app.budget.domain.Categorie;
import com.app.budget.domain.Projet;
import com.app.budget.events.BeforeDeleteProjet;
import com.app.budget.model.CategorieDTO;
import com.app.budget.repos.CategorieRepository;
import com.app.budget.repos.ProjetRepository;
import com.app.budget.util.NotFoundException;
import com.app.budget.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final ProjetRepository projetRepository;

    public CategorieService(final CategorieRepository categorieRepository,
            final ProjetRepository projetRepository) {
        this.categorieRepository = categorieRepository;
        this.projetRepository = projetRepository;
    }

    public List<CategorieDTO> findAll() {
        final List<Categorie> categories = categorieRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .toList();
    }

    public CategorieDTO get(final Long id) {
        return categorieRepository.findById(id)
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategorieDTO categorieDTO) {
        final Categorie categorie = new Categorie();
        mapToEntity(categorieDTO, categorie);
        return categorieRepository.save(categorie).getId();
    }

    public void update(final Long id, final CategorieDTO categorieDTO) {
        final Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categorieDTO, categorie);
        categorieRepository.save(categorie);
    }

    public void delete(final Long id) {
        final Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        categorieRepository.delete(categorie);
    }

    private CategorieDTO mapToDTO(final Categorie categorie, final CategorieDTO categorieDTO) {
        categorieDTO.setId(categorie.getId());
        categorieDTO.setCode(categorie.getCode());
        categorieDTO.setLibelle(categorie.getLibelle());
        categorieDTO.setProjetId(categorie.getProjetId() == null ? null : categorie.getProjetId().getId());
        return categorieDTO;
    }

    private Categorie mapToEntity(final CategorieDTO categorieDTO, final Categorie categorie) {
        categorie.setCode(categorieDTO.getCode());
        categorie.setLibelle(categorieDTO.getLibelle());
        final Projet projetId = categorieDTO.getProjetId() == null ? null : projetRepository.findById(categorieDTO.getProjetId())
                .orElseThrow(() -> new NotFoundException("projetId not found"));
        categorie.setProjetId(projetId);
        return categorie;
    }

    public boolean codeExists(final String code) {
        return categorieRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return categorieRepository.existsByLibelleIgnoreCase(libelle);
    }

    @EventListener(BeforeDeleteProjet.class)
    public void on(final BeforeDeleteProjet event) {
        final ReferencedException referencedException = new ReferencedException();
        final Categorie projetIdCategorie = categorieRepository.findFirstByProjetIdId(event.getId());
        if (projetIdCategorie != null) {
            referencedException.setKey("projet.categorie.projetId.referenced");
            referencedException.addParam(projetIdCategorie.getId());
            throw referencedException;
        }
    }

}
