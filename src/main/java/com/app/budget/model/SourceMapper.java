package com.app.budget.model;

import com.app.budget.domain.SourceFinacement;
import org.springframework.stereotype.Component;

@Component
public class SourceMapper {
   // public SourceMapper() {}
    public static  SourceMapper getInstance() {return new SourceMapper();}
    public SourceFinacementDTO mapToDTO(final SourceFinacement sourceFinacement) {
        SourceFinacementDTO sourceFinacementDTO = new SourceFinacementDTO();
        sourceFinacementDTO.setId(sourceFinacement.getId());
        sourceFinacementDTO.setCode(sourceFinacement.getCode());
        sourceFinacementDTO.setLibelle(sourceFinacement.getLibelle());
        sourceFinacementDTO.setIdTypeSourcefinancement(sourceFinacement.getTypSourceFinancement()!=null?
                sourceFinacement.getTypSourceFinancement().getId():null);
        sourceFinacementDTO.setTypSourceFinancement(sourceFinacement.getTypSourceFinancement()!=null?
                TypeSource.getInstance().mapToDTO(sourceFinacement.getTypSourceFinancement()) : null);
        return sourceFinacementDTO;
    }

    public SourceFinacement mapToEntity(final SourceFinacementDTO sourceFinacementDTO) {
        SourceFinacement sourceFinacement = new SourceFinacement();
        sourceFinacement.setId(sourceFinacementDTO.getId());
        sourceFinacement.setCode(sourceFinacementDTO.getCode());
        sourceFinacement.setLibelle(sourceFinacementDTO.getLibelle());
        sourceFinacement.setTypSourceFinancement(sourceFinacementDTO.getIdTypeSourcefinancement()!=null?
                TypeSource.getInstance().mapToEntity(new TypSourceFinancementDTO(sourceFinacementDTO.getIdTypeSourcefinancement())): null);
        return sourceFinacement;
    }
}
