package com.app.budget.tresorerie.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteBancaireDto {
    private Long id;
    private String numero;
    private Long idDevise;
    private Long idBanque;
    private Long idComteComptable;
    private Long sourceFinacementId;
}
