package com.app.budget.model;

import com.app.budget.domain.Classe;

public class ClasseMapper {

    public static  ClasseMapper getInstance() {return new ClasseMapper();}

    public ClasseDTO mapToDTO(final Classe classe) {
        ClasseDTO classeDTO = new ClasseDTO();
        classeDTO.setId(classe.getId());
        classeDTO.setLibelle(classe.getLibelle());
        classeDTO.setType(classe.getType());
        return classeDTO;
    }

    public Classe mapToEntity(final ClasseDTO classeDTO) {
        Classe classe = new Classe();
        classe.setId(classeDTO.getId());
        classe.setLibelle(classeDTO.getLibelle());
        classe.setType(classeDTO.getType());
        return classe;
    }
}
