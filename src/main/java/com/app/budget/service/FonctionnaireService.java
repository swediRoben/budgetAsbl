package com.app.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.domain.Fonctionnaire;
import com.app.budget.domain.RoleSysteme;
import com.app.budget.repos.FonctionnaireRepository;

@Service
public class FonctionnaireService {
        private final FonctionnaireRepository repository;

    public FonctionnaireService(FonctionnaireRepository repository) {
        this.repository = repository;
    }

    public Fonctionnaire save(Fonctionnaire f) {
        return repository.save(f);
    }

    public List<Fonctionnaire> findAll() {
        return repository.findAll();
    }

     public List<Fonctionnaire> findResponsable() {
        return repository.findByRoleSysteme(RoleSysteme.RESPONSABLE);
    }

    public Fonctionnaire findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnaire introuvable"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Fonctionnaire updata(Long id, Fonctionnaire fonctionnaire) {
        if (!repository.existsById(id)) {
            return null;
        }
        fonctionnaire.setId(id);
        return repository.save(fonctionnaire);
    }
}
