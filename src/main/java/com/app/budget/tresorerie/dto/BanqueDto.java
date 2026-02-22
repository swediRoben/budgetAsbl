package com.app.budget.tresorerie.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanqueDTO {
    private Long id;
    private String libelle;
    private Boolean actif;
    private Long idCompteComptable;
}
