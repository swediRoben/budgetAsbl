package com.app.budget.model;

import com.app.budget.domain.Projet;

public class ProjetMapper {
    public static  ProjetMapper getInstance() {return new ProjetMapper();}
    public ProjetDTO mapToDTO(final Projet projet) {
        ProjetDTO projetDTO = new ProjetDTO();
        projetDTO.setId(projet.getId());
        projetDTO.setCode(projet.getCode());
        projetDTO.setLibelle(projet.getLibelle());
        projetDTO.setDateDebut(projet.getDateDebut());
        projetDTO.setDateFin(projet.getDateFin());
        // projetDTO.setIdprojet(projet.getIdprojet());
        return projetDTO;
    }

    public Projet mapToEntity(final ProjetDTO projetDTO) {
        Projet projet = new Projet();
        projet.setId(projetDTO.getId());
        projet.setCode(projetDTO.getCode());
        projet.setLibelle(projetDTO.getLibelle());
        projet.setDateDebut(projetDTO.getDateDebut());
        projet.setDateFin(projetDTO.getDateFin());
        //projet.setIdprojet(projetDTO.getIdprojet());
        return projet;
    }
}
