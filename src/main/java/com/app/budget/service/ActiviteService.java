package com.app.budget.service;

import com.app.budget.domain.Activite;
import com.app.budget.domain.Categorie;
import com.app.budget.model.ActiviteDTO;
import com.app.budget.model.CategorieDTO;
import com.app.budget.repos.ActiviteRepository;
import com.app.budget.repos.CategorieRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final CategorieService categorieService;
    private final CategorieRepository categorieRepository;

    public ActiviteService(final ActiviteRepository activiteRepository,
                           CategorieService categorieService,
                           CategorieRepository categorieRepository) {
        this.activiteRepository = activiteRepository;
        this.categorieService = categorieService;
        this.categorieRepository = categorieRepository;
    }

    public List<ActiviteDTO> findAll(Long project,Long categorieId) {

        //System.out.println("Ruragira   "+activiteRepository.findAllByCategorieId_ProjetId_Id(10029L).size());
        List<Activite> activites=null;
        if(project==null&&categorieId==null){
            activites=activiteRepository.findAll(Sort.by("id"));
        }else {
            activites=activiteRepository.findAllByCategorieId_ProjetId_IdOrCategorieId_Id(project,categorieId);
        }

        /*final List<Activite> activites = project==null||categorieId==null?
                activiteRepository.findAll(Sort.by("id")):
                activiteRepository.findAllByCategorieIdProjetId_IdOrCategorieId_Id(project,categorieId);*/
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
        CategorieDTO categorieDTO=activite.getCategorieId()!=null?categorieService.mapToDTO(activite.getCategorieId(),new CategorieDTO()):null;
        activiteDTO.setProjet(categorieDTO!=null?categorieDTO.getProjet():null);
        activiteDTO.setProjetId(categorieDTO!=null ?categorieDTO.getProjet().getId():null);
        activiteDTO.setCategorieId(activite.getCategorieId()!=null?activite.getCategorieId().getId():null);
        activiteDTO.setCategorie(activite.getCategorieId()!=null?
                categorieService.mapToDTO(activite.getCategorieId(),new CategorieDTO()):null);
        return activiteDTO;
    }

    private Activite mapToEntity(final ActiviteDTO activiteDTO, final Activite activite) {
        activite.setId(activiteDTO.getId());
        activite.setCode(activiteDTO.getCode());
        activite.setLibelle(activiteDTO.getLibelle());

        activite.setCategorieId(activiteDTO.getCategorieId()!=null?categorieRepository.findById(activiteDTO.getCategorieId()).orElseThrow(
                () -> new NotFoundException("CategorieId not found")
        ):null);
        return activite;
    }

    public boolean codeExists(final String code) {
        return activiteRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return activiteRepository.existsByLibelleIgnoreCase(libelle);
    }

}
