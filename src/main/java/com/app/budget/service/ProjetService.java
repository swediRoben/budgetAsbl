package com.app.budget.service;

import com.app.budget.domain.Projet;
import com.app.budget.events.BeforeDeleteProjet;
import com.app.budget.model.ProjetDTO;
import com.app.budget.model.ProjetMapper;
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
                .map(projet -> ProjetMapper.getInstance().mapToDTO(projet))
                .toList();
    }

    public ProjetDTO get(final Long id) {
        return projetRepository.findById(id)
                .map(projet -> ProjetMapper.getInstance().mapToDTO(projet))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProjetDTO projetDTO) {
        final Projet projet = new Projet();

        return projetRepository.save(ProjetMapper.getInstance().mapToEntity(projetDTO)).getId();
    }

    public void update(final Long id, final ProjetDTO projetDTO) {
        final Projet projet = projetRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        projetRepository.save( ProjetMapper.getInstance().mapToEntity(projetDTO) );
    }

    public void delete(final Long id) {
        final Projet projet = projetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteProjet(id));
        projetRepository.delete(projet);
    }



    public boolean codeExists(final String code) {
        return projetRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return projetRepository.existsByLibelleIgnoreCase(libelle);
    }

}
