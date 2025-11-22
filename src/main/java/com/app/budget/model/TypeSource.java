package com.app.budget.model;

import com.app.budget.domain.TypSourceFinancement;

public class TypeSource {

    public static TypeSource getInstance() {return new TypeSource();}

    public TypSourceFinancementDTO mapToDTO(final TypSourceFinancement typSourceFinancement) {
        TypSourceFinancementDTO  typSourceFinancementDTO = new TypSourceFinancementDTO();
        typSourceFinancementDTO.setId(typSourceFinancement.getId());
        typSourceFinancementDTO.setLibelle(typSourceFinancement.getLibelle());
        return typSourceFinancementDTO;
    }

    public TypSourceFinancement mapToEntity(final TypSourceFinancementDTO typSourceFinancementDTO) {
        TypSourceFinancement typSourceFinancement = new TypSourceFinancement();
        typSourceFinancement.setId(typSourceFinancementDTO.getId());
        typSourceFinancement.setLibelle(typSourceFinancementDTO.getLibelle());
        return typSourceFinancement;
    }
}
