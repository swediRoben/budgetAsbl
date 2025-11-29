package com.app.budget.service;

import com.app.budget.domain.Exercice;
import com.app.budget.model.ExerciceDTO;
import com.app.budget.model.ExerciceMapper;
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
                .map(c-> ExerciceMapper.getInstance().mapToDTO(c))
                .toList();
    }

    public ExerciceDTO get(final Long id) {
        return exerciceRepository.findById(id)
                .map(b-> ExerciceMapper.getInstance().mapToDTO(b))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExerciceDTO exerciceDTO) { 
  
        return exerciceRepository.save(ExerciceMapper.getInstance().mapToEntity(exerciceDTO)).getId();
    }

    public void update(final Long id, final ExerciceDTO exerciceDTO) {
        final Exercice exercice = exerciceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
       if (exerciceDTO.getCloture()) {
          exerciceDTO.setPreparation(false);;  
          exerciceDTO.setExecution(false);;  
        }
        if (exerciceDTO.getExecution()) {
          exerciceDTO.setPreparation(false);;  
          exerciceDTO.setCloture(false);;  
        } 
        exerciceRepository.save(ExerciceMapper.getInstance().mapToEntity(exerciceDTO));
    }

    public void delete(final Long id) {
        final Exercice exercice = exerciceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        exerciceRepository.delete(exercice);
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
