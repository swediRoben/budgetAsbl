package com.app.budget.tresorerie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.constate.TypeClasse; 
import com.app.budget.tresorerie.dto.OperationComptableDetailDto;
import com.app.budget.tresorerie.dto.OperationComptableDto;
import com.app.budget.tresorerie.entity.OperationComptable;
import com.app.budget.tresorerie.entity.OperationComptableDetail;
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

    public OperationComptableDto update(Long id, OperationComptableDto dto) {
     if (repositories.existsById(id)) {
      existByClasseIdNot(dto.getClasseid(), id);
        dto.setId(id);
        OperationComptable d=repositories.save(toEntity(dto));
         return  toDto(d); 
       }else{
         throw new UnsupportedOperationException("Unimplemented method 'delete'");
       }   
    }

    public OperationComptableDto getById(Long id) {
         return toDto(repositories.findById(id).get());      
    }

    public void existByClasse(Long idClasse){
      if (repositories.existsByClasseid(idClasse)) {
        throw new UnsupportedOperationException("Classe exist déjà");
      }
    } 

   public void existByClasseIdNot(Long idClasse,Long id){
      if (repositories.existsByClasseidAndIdNot(idClasse,id)) {
        throw new UnsupportedOperationException("Classe exist déjà");
      }
    } 

    public List<OperationComptableDto> getAll(TypeClasse type) {
       List<OperationComptableDto> data =new ArrayList<>();
       if (type!=null) {
         List<OperationComptable> datas =repositories.findAllByType(type);
         for (OperationComptable operationComptable : datas) {
              data.add(toDto(operationComptable));
         } 
       }else{
           List<OperationComptable> datas =repositories.findAll();
         for (OperationComptable operationComptable : datas) {
              data.add(toDto(operationComptable));
         }  
       }
       return data;
    }

    public OperationComptableDto create(OperationComptableDto dto) {
      existByClasse(dto.getClasseid());
       OperationComptable saved= repositories.save(toEntity(dto)); 
         if (saved!=null) {
         return toDto(saved);
       }
         throw new UnsupportedOperationException("Unimplemented method 'delete'");
    
    }

    public OperationComptableDto toDto(OperationComptable d){
      OperationComptableDto data=new OperationComptableDto();
      data.setId(d.getId());
      data.setClasseid(d.getClasseid());
      data.setType(d.getType());
      data.setClasse(d.getClasse()); 
      if (!d.getDetails().isEmpty()) {
       d.getDetails().forEach(v->{
         OperationComptableDetailDto det=new OperationComptableDetailDto();
         det.setId(v.getId());
         det.setCreditid(v.getCreditid());
         det.setCredit(v.getCredit());
         det.setDebit(v.getDebit());
         det.setDebitid(v.getDebitid());
         data.getDetails().add(det);
      });
      }
      return data;
    }

     public OperationComptable toEntity(OperationComptableDto d){
      OperationComptable data=new OperationComptable();
      data.setId(d.getId());
      data.setClasseid(d.getClasseid());
      data.setType(d.getType()); 
      d.getDetails().forEach(v->{
         OperationComptableDetail det=new OperationComptableDetail();
         det.setId(v.getId());
         det.setCreditid(v.getCreditid());
         det.setDebitid(v.getDebitid());
         det.setOperationComptable(data);
         data.getDetails().add(det);
      });

      return data;
    }
    
}
