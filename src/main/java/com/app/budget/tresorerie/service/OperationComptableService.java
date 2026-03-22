package com.app.budget.tresorerie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.constate.TypeClasse;
import com.app.budget.tresorerie.dto.BanqueDTO;
import com.app.budget.tresorerie.entity.OperationComptable;
import com.app.budget.tresorerie.repository.OperationComptableRepositories;

@Service
public class OperationComptableService {
    private final OperationComptableRepositories repositories;
    

    public OperationComptableService(OperationComptableRepositories repositories) {
        this.repositories = repositories;
    }

    public void delete(Long id) { 
       if (repositories.existsById(id)) {
          repositories.deleteById(id);
       }else{
         throw new UnsupportedOperationException("Unimplemented method 'delete'");
       }
    }

    public OperationComptable update(Long id, OperationComptable dto) {
     if (repositories.existsById(id)) {
        dto.setId(id);
         return  repositories.save(dto); 
       }else{
         throw new UnsupportedOperationException("Unimplemented method 'delete'");
       }   
    }

    public OperationComptable getById(Long id) {
         return repositories.findById(id).get();      
    }

    public List<OperationComptable> getAll(TypeClasse type) {
       List<OperationComptable> data =new ArrayList<>();
       if (type!=null) {
        data.addAll(repositories.findAllByType(type));
       }else{
        data.addAll(repositories.findAll()); 
       }
       return data;
    }

    public OperationComptable create(OperationComptable dto) {
       OperationComptable saved= repositories.save(dto); 
         if (saved!=null) {
         return saved;
       }
         throw new UnsupportedOperationException("Unimplemented method 'delete'");
    
    }
    
}
