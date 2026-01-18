package com.app.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.domain.Utilisateur;
import com.app.budget.repos.UtilisateurRepository;

@Service
public class UtilisateurService {
    private final UtilisateurRepository repository;

    public UtilisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    public Utilisateur save(Utilisateur f) {
        return repository.save(f);
    }

    public List<Utilisateur> findAll() {
        return repository.findAll();
    }

    public Utilisateur findByEmail(String email) {
        return repository.findByEmail(email)
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Utilisateur updata(Long id, Utilisateur dto) {
        if (!repository.existsById(id)) {
            return null;
        }
        dto.setId(id);
        return repository.save(dto);
    }

}
