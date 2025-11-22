package com.app.budget.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjetDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @ProjetCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    @ProjetLibelleUnique
    private String libelle;

    @NotNull
    private LocalDate dateDebut;

    private LocalDate dateFin;

    //private Long idprojet;

    public ProjetDTO(Long id) {
        this.id = id;
    }

    public ProjetDTO() {}
}
