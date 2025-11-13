package com.app.budget.service;

import com.app.budget.domain.Classe;
import com.app.budget.model.ClasseDTO;
import com.app.budget.repos.ClasseRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClasseService {

    private final ClasseRepository classeRepository;

    public ClasseService(final ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public List<ClasseDTO> findAll() {
        final List<Classe> classes = classeRepository.findAll(Sort.by("id"));
        return classes.stream()
                .map(classe -> mapToDTO(classe, new ClasseDTO()))
                .toList();
    }

    public ClasseDTO get(final Long id) {
        return classeRepository.findById(id)
                .map(classe -> mapToDTO(classe, new ClasseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ClasseDTO classeDTO) {
        final Classe classe = new Classe();
        mapToEntity(classeDTO, classe);
        return classeRepository.save(classe).getId();
    }

    public void update(final Long id, final ClasseDTO classeDTO) {
        final Classe classe = classeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(classeDTO, classe);
        classeRepository.save(classe);
    }

    public void delete(final Long id) {
        final Classe classe = classeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        classeRepository.delete(classe);
    }

    private ClasseDTO mapToDTO(final Classe classe, final ClasseDTO classeDTO) {
        classeDTO.setId(classe.getId());
        classeDTO.setLibelle(classe.getLibelle());
        return classeDTO;
    }

    private Classe mapToEntity(final ClasseDTO classeDTO, final Classe classe) {
        classe.setLibelle(classeDTO.getLibelle());
        return classe;
    }

    public boolean libelleExists(final String libelle) {
        return classeRepository.existsByLibelleIgnoreCase(libelle);
    }

}
