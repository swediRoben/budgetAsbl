package com.app.budget.service;

import com.app.budget.domain.Exercice;
import com.app.budget.model.ExerciceDTO;
import com.app.budget.repos.ExerciceRepository;
import com.app.budget.util.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ExerciceService {

    private final ExerciceRepository exerciceRepository;

    public ExerciceService(final ExerciceRepository exerciceRepository) {
        this.exerciceRepository = exerciceRepository;
    }

    public List<ExerciceDTO> findAll() {
        final List<Exercice> exercices = exerciceRepository.findAll(Sort.by("id"));
        return exercices.stream()
                .map(exercice -> mapToDTO(exercice, new ExerciceDTO()))
                .toList();
    }

    public ExerciceDTO get(final Long id) {
        return exerciceRepository.findById(id)
                .map(exercice -> mapToDTO(exercice, new ExerciceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExerciceDTO exerciceDTO) {
        final Exercice exercice = new Exercice();
        mapToEntity(exerciceDTO, exercice);
        return exerciceRepository.save(exercice).getId();
    }

    public void update(final Long id, final ExerciceDTO exerciceDTO) {
        final Exercice exercice = exerciceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(exerciceDTO, exercice);
        exerciceRepository.save(exercice);
    }

    public void delete(final Long id) {
        final Exercice exercice = exerciceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        exerciceRepository.delete(exercice);
    }

    private ExerciceDTO mapToDTO(final Exercice exercice, final ExerciceDTO exerciceDTO) {
        exerciceDTO.setId(exercice.getId());
        exerciceDTO.setCode(exercice.getCode());
        exerciceDTO.setLibelle(exercice.getLibelle());
        exerciceDTO.setDateDebut(exercice.getDateDebut());
        exerciceDTO.setDateFin(exercice.getDateFin());
        exerciceDTO.setCloture(exercice.getCloture());
        return exerciceDTO;
    }

    private Exercice mapToEntity(final ExerciceDTO exerciceDTO, final Exercice exercice) {
        exercice.setCode(exerciceDTO.getCode());
        exercice.setLibelle(exerciceDTO.getLibelle());
        exercice.setDateDebut(exerciceDTO.getDateDebut());
        exercice.setDateFin(exerciceDTO.getDateFin());
        exercice.setCloture(exerciceDTO.getCloture());
        return exercice;
    }

    public boolean codeExists(final String code) {
        return exerciceRepository.existsByCodeIgnoreCase(code);
    }

    public boolean libelleExists(final String libelle) {
        return exerciceRepository.existsByLibelleIgnoreCase(libelle);
    }

    public boolean dateDebutExists(final LocalDate dateDebut) {
        return exerciceRepository.existsByDateDebut(dateDebut);
    }

    public boolean dateFinExists(final LocalDate dateFin) {
        return exerciceRepository.existsByDateFin(dateFin);
    }

}
