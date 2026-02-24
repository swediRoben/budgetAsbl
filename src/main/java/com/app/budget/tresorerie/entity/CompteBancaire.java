package com.app.budget.tresorerie.entity; 
import com.app.budget.constate.TypeCompte;
import com.app.budget.domain.SourceFinacement;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compte_comptable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteBancaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private Long idDevise;

    @ManyToOne
    @JoinColumn(name = "id_banque")
    private Banque banque;

    private Long idComteComptable;

     @Enumerated(EnumType.STRING)
    private TypeCompte typeCompte;
        
    @ManyToOne
    @JoinColumn(name = "id_sourceFinacement")
    private SourceFinacement sourceFinacement;
}
