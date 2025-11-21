package com.app.budget.service;

import com.app.budget.domain.Projet;
import com.app.budget.events.BeforeDeleteProjet;
import com.app.budget.model.ProjetDTO;
import com.app.budget.repos.ProjetRepository;
import com.app.budget.util.NotFoundException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProjetService {

    private final ProjetRepository projetRepository;
    private final ApplicationEventPublisher publisher;

    public ProjetService(final ProjetRepository projetRepository,
            final ApplicationEventPublisher publisher) {
        this.projetRepository = projetRepository;
        this.publisher = publisher;
    }

    public List<ProjetDTO> findAll(String  libelle, LocalDate dateDebut, LocalDate dateFin) {
        List<Projet>list=null;
        if(libelle==null || dateDebut==null || dateFin==null){
            list=projetRepository.findAll(Sort.by("id"));
        }
        if(libelle!=null && dateDebut!=null && dateFin!=null){
            list=projetRepository.findByLibelleAndDateCreatedBetween(libelle,dateDebut.atStartOfDay().atOffset(ZoneOffset.UTC),
                    dateFin.atStartOfDay().atOffset(ZoneOffset.UTC));
        }
        //final List<Projet> projets = projetRepository.findAll(Sort.by("id"));
        return list.stream()
                .map(projet -> mapToDTO(projet, new ProjetDTO()))
                .toList();
    }

    public ProjetDTO get(final Long id) {
        return projetRepository.findById(id)
                .map(projet -> mapToDTO(projet, new ProjetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProjetDTO projetDTO) {
        final Projet projet = new Projet();
        mapToEntity(projetDTO, projet);
        return projetRepository.save(projet).getId();
    }

    public void update(final Long id, final ProjetDTO projetDTO) {
        final Projet projet = projetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(projetDTO, projet);
        projetRepository.save(projet);
    }

    public void delete(final Long id) {
        final Projet projet = projetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteProjet(id));
        projetRepository.delete(projet);
    }

    public ProjetDTO mapToDTO(final Projet projet, final ProjetDTO projetDTO) {
        projetDTO.setId(projet.getId());
        projetDTO.setCode(projet.getCode());
        projetDTO.setLibelle(projet.getLibelle());
        projetDTO.setDateDebut(projet.getDateDebut());
        projetDTO.setDateFin(projet.getDateFin());
       // projetDTO.setIdprojet(projet.getIdprojet());
        return projetDTO;
    }

    private Projet mapToEntity(final ProjetDTO projetDTO, final Projet projet) {
        projet.setCode(projetDTO.getCode());
        projet.setLibelle(projetDTO.getLibelle());
        projet.setDateDebut(projetDTO.getDateDebut());
        projet.setDateFin(projetDTO.getDateFin());
        //projet.setIdprojet(projetDTO.getIdprojet());
        return projet;
    }

    public boolean codeExists(final String code) {
        return projetRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return projetRepository.existsByLibelleIgnoreCase(libelle);
    }

}
