package com.app.budget.service;

import com.app.budget.domain.PlanComptable;
import com.app.budget.model.PlanComptableDTO;
import com.app.budget.repos.PlanComptableRepository;
import com.app.budget.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanComptableService {

    private final PlanComptableRepository planComptableRepository;

    public PlanComptableService(final PlanComptableRepository planComptableRepository) {
        this.planComptableRepository = planComptableRepository;
    }

    public List<PlanComptableDTO> findAll() {
        final List<PlanComptable> planComptables = planComptableRepository.findAll(Sort.by("id"));
        return planComptables.stream()
                .map(planComptable -> mapToDTO(planComptable, new PlanComptableDTO()))
                .toList();
    }

    public PlanComptableDTO get(final Long id) {
        return planComptableRepository.findById(id)
                .map(planComptable -> mapToDTO(planComptable, new PlanComptableDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanComptableDTO planComptableDTO) {
        final PlanComptable planComptable = new PlanComptable();
        mapToEntity(planComptableDTO, planComptable);
        return planComptableRepository.save(planComptable).getId();
    }

    public void update(final Long id, final PlanComptableDTO planComptableDTO) {
        final PlanComptable planComptable = planComptableRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planComptableDTO, planComptable);
        planComptableRepository.save(planComptable);
    }

    public void delete(final Long id) {
        final PlanComptable planComptable = planComptableRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        planComptableRepository.delete(planComptable);
    }

    private PlanComptableDTO mapToDTO(final PlanComptable planComptable,
            final PlanComptableDTO planComptableDTO) {
        planComptableDTO.setId(planComptable.getId());
        planComptableDTO.setClasse(planComptable.getClasse());
        planComptableDTO.setLibelle(planComptable.getLibelle());
        planComptableDTO.setNumero(planComptable.getNumero());
        planComptableDTO.setSens(planComptable.getSens());
        return planComptableDTO;
    }

    private PlanComptable mapToEntity(final PlanComptableDTO planComptableDTO,
            final PlanComptable planComptable) {
        planComptable.setClasse(planComptableDTO.getClasse());
        planComptable.setLibelle(planComptableDTO.getLibelle());
        planComptable.setNumero(planComptableDTO.getNumero());
        planComptable.setSens(planComptableDTO.getSens());
        return planComptable;
    }

    public boolean libelleExists(final String libelle) {
        return planComptableRepository.existsByLibelleIgnoreCase(libelle);
    }

    public boolean numeroExists(final String numero) {
        return planComptableRepository.existsByNumeroIgnoreCase(numero);
    }

}
